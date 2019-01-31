package com.fanjun.processroute.request;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.fanjun.processroute.AidlRoute;
import com.fanjun.processroute.ServiceCallback;
import com.fanjun.processroute.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionProcessor extends Thread implements ServiceConnection {

    private AidlRoute aidlRoute;
    private BlockingQueue<RouteReq> routeReqs;
    private Map<String, RouteReq> waltCallbackRouteReqs;
    private ServiceConnectionState connectionState;
    private Object object = new Object();
    private Handler handler;
    private ServiceCallback serviceCallback;
    private String processId;

    public ConnectionProcessor(Context context, RouteReq routeReq) {
        processId = context.getPackageName();
        routeReqs = new LinkedBlockingQueue<RouteReq>();
        waltCallbackRouteReqs = new HashMap<>();
        handler = new Handler();
        serviceCallback = new ServiceCallback.Stub() {
            @Override
            public synchronized void callback(final String callbackId, final boolean success, final String resJson, final String resCls, final String errorMsg) throws RemoteException {
                if (waltCallbackRouteReqs != null) {
                    final RouteReq routeReq = waltCallbackRouteReqs.get(callbackId);
                    if (routeReq != null) {
                        if (routeReq.getRouteListener() != null) {
                            handler.postAtFrontOfQueue(new Runnable() {
                                @Override
                                public void run() {
                                    if (success) {
                                        if (!TextUtils.isEmpty(resCls)) {
                                            try {
                                                Class resClass = Class.forName(resCls);
                                                routeReq.getRouteListener().callback(JsonUtils.getInstance().fromJson(resJson, resClass));
                                            } catch (ClassNotFoundException e) {
                                                e.printStackTrace();
                                                routeReq.getRouteListener().fail(e.getMessage());
                                            } catch (ClassCastException e) {
                                                e.printStackTrace();
                                                routeReq.getRouteListener().fail("与" + routeReq.getRemoteClass() + "远程类的" + routeReq.getMethod() + "回调参数不一致");
                                            }
                                        } else {
                                            routeReq.getRouteListener().callback(null);
                                        }
                                    } else {
                                        routeReq.getRouteListener().fail(errorMsg);
                                    }
                                    waltCallbackRouteReqs.remove(callbackId);

                                }
                            });
                        }
                    }
                    waltCallbackRouteReqs.remove(callbackId);
                }
            }
        };
        startConnect(context, routeReq);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        aidlRoute = AidlRoute.Stub.asInterface(service);
        try {
            aidlRoute.registCallback(processId, serviceCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.connectionState = ServiceConnectionState.CONNECTED;
        synchronized (object) {
            object.notify();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        this.connectionState = ServiceConnectionState.DISCONNECTED;
        synchronized (object) {
            object.notify();
        }
    }

    public void connect(Context context, final RouteReq routeReq) {
        if (routeReq.getRouteListener() != null) {
            handler.postAtFrontOfQueue(new Runnable() {
                @Override
                public void run() {
                    routeReq.getRouteListener().prepare();
                }
            });
        }
        routeReqs.offer(routeReq);
        if (connectionState == ServiceConnectionState.DISCONNECTED) {
            startConnect(context, routeReq);
        }
    }

    private void startConnect(Context context, RouteReq routeReq) {
        connectionState = ServiceConnectionState.CONNECTING;
        context.startService(new Intent(routeReq.getProcessId() + ".AidlRouteService"));
        if (!context.bindService(new Intent(routeReq.getProcessId() + ".AidlRouteService"), this, Context.BIND_AUTO_CREATE)) {
            connectionState = ServiceConnectionState.DISCONNECTED;
            if (routeReq != null){
                routeReq.getRouteListener().fail("远程协议"+routeReq.getRemoteClass()+"指明ProgressId无法启动");
            }
        }
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                if (connectionState == ServiceConnectionState.CONNECTING) {
                    synchronized (object) {
                        object.wait();
                    }
                }
                final RouteReq routeReq = routeReqs.take();
                final RouteListener routeListener = routeReq.getRouteListener();
                if (connectionState == ServiceConnectionState.DISCONNECTED) {
                    if (routeListener != null) {
                        handler.postAtFrontOfQueue(new Runnable() {
                            @Override
                            public void run() {
                                routeListener.fail("连接" + routeReq.getProcessId() + "失败，请启动该组件！");
                            }
                        });
                    }
                } else if (aidlRoute == null) {
                    if (routeListener != null) {
                        handler.postAtFrontOfQueue(new Runnable() {
                            @Override
                            public void run() {
                                routeListener.fail("连接" + routeReq.getProcessId() + "失败，请启动该组件！");
                            }
                        });
                    }
                } else {
                    String callbackId = routeReq.toString();
                    //如前台未使用回调，则认为不需要回调，防止内存泄漏
                    if (routeReq.getRouteListener() != null) {
                        waltCallbackRouteReqs.put(callbackId, routeReq);
                    }
                    aidlRoute.onEvent(routeReq.getRemoteClass(), routeReq.getMethod(),
                            routeReq.getParams() == null ? null : routeReq.getParamsJson(),
                            routeReq.getParamClss() == null ? null : routeReq.getParamClssJson(),
                            callbackId,
                            processId);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
