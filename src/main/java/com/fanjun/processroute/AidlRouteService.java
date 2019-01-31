package com.fanjun.processroute;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.fanjun.processroute.callback.CallbackEntity;
import com.fanjun.processroute.callback.CallbackProcessor;
import com.fanjun.processroute.remote.RemoteServiceImpl;
import com.fanjun.processroute.utils.InvokeMethodUtils;
import com.fanjun.processroute.utils.JsonUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class AidlRouteService extends Service {
    public static Map<String, ServiceCallback> callbackMap;
    @Override
    public void onCreate() {
        super.onCreate();
        if (callbackMap == null){
            callbackMap = new HashMap<>();
        }else{
            callbackMap.clear();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final AidlRoute.Stub mBinder = new AidlRoute.Stub() {
        public void onEvent(final String remoteCls, final String method, String paramsJson, String paramClssJson, String callbackId, String progressId) {
            //获取到调用的方法的方法参数
            final Object[] params = restoreParams(paramsJson, paramClssJson);
            //生成回调处理器
            final CallbackProcessor callbackProcessor = createCallbackProcessor(callbackId, progressId);
            //调用远程方法
            invokeRemoteMethod(remoteCls, method, params, callbackProcessor);
        }

        @Override
        public void registCallback(String packageName, ServiceCallback callback) throws RemoteException {
            callbackMap.put(packageName, callback);
        }
    };

    /**
     * 生成回调处理器
     * @param callbackId 回调处理器唯一标识
     * @param progressId 回调进程id
     * @return
     */
    private CallbackProcessor createCallbackProcessor(String callbackId, String progressId){
        CallbackEntity callbackEntity = new CallbackEntity();
        callbackEntity.setCallbackId(callbackId);
        callbackEntity.setFromPackageName(progressId);
        return new CallbackProcessor(callbackEntity);
    }

    /**
     * 调用远程方法
     *
     * @param remoteCls         远程方法类
     * @param method            远程方法名
     * @param params            远程方法参数
     * @param callbackProcessor 进程回调处理器
     */
    private void invokeRemoteMethod(String remoteCls, String method, Object[] params, CallbackProcessor callbackProcessor) {
        if (params.length>10){
            //参数数量不能超过10个
            callbackProcessor.callFail(remoteCls+"的"+method+"协议"+"参数数量不能超过10个!");
            return;
        }
        try {
            Class remoteClass = Class.forName(remoteCls);
            if (remoteClass.isAnnotationPresent(RemoteServiceImpl.class)){
                RemoteServiceImpl remoteServiceImpl = (RemoteServiceImpl) remoteClass.getAnnotation(RemoteServiceImpl.class);
                String remoteServiceImplCls = remoteServiceImpl.value();
                try {
                    Class remoteServiceImplClass = Class.forName(remoteServiceImplCls);
                    InvokeMethodUtils.getInstance().invokeMethod(remoteServiceImplClass, method, params, callbackProcessor);
                }catch (ClassNotFoundException e){
                    //未找到远程协议类
                    callbackProcessor.callFail("未找到"+remoteCls+"的"+remoteServiceImplCls+"实现类");
                }
            }else{
                //未声明RemoteServiceImpl实现类
                callbackProcessor.callFail(remoteCls+"未声明RemoteServiceImpl实现类!");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //未找到远程协议类
            callbackProcessor.callFail("未找到"+remoteCls+"远程协议类");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            //未找到远程方法
            callbackProcessor.callFail("未找到"+remoteCls+"远程协议的"+method+"方法，请检查方法名或参数结构");
        } catch (IllegalAccessException e){
            e.printStackTrace();
            //远程方法调用异常
            callbackProcessor.callFail(remoteCls+"的"+method+"调用异常，"+e.getMessage());
        } catch (InvocationTargetException e){
            e.printStackTrace();
            //远程方法调用异常
            callbackProcessor.callFail(remoteCls+"的"+method+"调用异常，"+e.getCause().toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
            //远程方法调用异常
            callbackProcessor.callFail(remoteCls+"的"+method+"调用异常，"+e.getMessage());
        }
    }

    /**
     * 还原原始参数对象
     *
     * @param paramsJson    原始参数json值
     * @param paramClssJson 原始参数类型json值
     * @return
     */
    private Object[] restoreParams(String paramsJson, String paramClssJson) {
        if (paramsJson == null || paramClssJson == null) {
            //如参数字符串为null，则认为本次未传递参数
            return new Object[]{};
        }
        //如参数字符串中有'null',则认为本次有传递参数，但传递参数值为null
        Object[] params = JsonUtils.getInstance().fromJson(paramsJson, Object[].class);
        String[] paramsCls = JsonUtils.getInstance().fromJson(paramClssJson, String[].class);
        Object[] newParams = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null) {
                try {
                    Class cls = Class.forName(paramsCls[i]);
                    newParams[i] = JsonUtils.getInstance().fromJson(JsonUtils.getInstance().toJson(params[i]), cls);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    newParams[i] = null;
                }
            } else {
                newParams[i] = null;
            }
        }
        return newParams;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
