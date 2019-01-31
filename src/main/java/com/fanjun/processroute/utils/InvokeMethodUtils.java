package com.fanjun.processroute.utils;

import com.fanjun.processroute.callback.CallbackProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class InvokeMethodUtils {
    private static InvokeMethodUtils invokeMethodUtils;
    private Map<String, Object> objectMap;
    private InvokeMethodUtils(){
        objectMap = new HashMap<>();
    }

    public static InvokeMethodUtils getInstance(){
        if (invokeMethodUtils == null){
            invokeMethodUtils = new InvokeMethodUtils();
        }
        return invokeMethodUtils;
    }

    public void invokeMethod(Class cls, String method, Object[] params, CallbackProcessor callbackProcessor) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException{
        int paramsLength = params.length;
        switch (paramsLength){
            case 0:
                invokeMethodParams0(cls, method, callbackProcessor);
                break;
            case 1:
                invokeMethodParams1(cls, method, params, callbackProcessor);
                break;
            case 2:
                invokeMethodParams2(cls, method, params, callbackProcessor);
                break;
            case 3:
                invokeMethodParams3(cls, method, params, callbackProcessor);
                break;
            case 4:
                invokeMethodParams4(cls, method, params, callbackProcessor);
                break;
            case 5:
                invokeMethodParams5(cls, method, params, callbackProcessor);
                break;
            case 6:
                invokeMethodParams6(cls, method, params, callbackProcessor);
                break;
            case 7:
                invokeMethodParams7(cls, method, params, callbackProcessor);
                break;
            case 8:
                invokeMethodParams8(cls, method, params, callbackProcessor);
                break;
            case 9:
                invokeMethodParams9(cls, method, params, callbackProcessor);
                break;
            case 10:
                invokeMethodParams10(cls, method, params, callbackProcessor);
                break;
        }
    }

    private Object getObject(Class cls) throws IllegalAccessException, InstantiationException{
        Object object = objectMap.get(cls.getName());
        if (object == null){
            object = cls.newInstance();
            objectMap.put(cls.getName(), object);
        }
        return object;
    }

    private void invokeMethodParams0(Class cls, String method, CallbackProcessor callbackProcessor) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Method remoteMethod = cls.getMethod(method, callbackProcessor.getClass());
        remoteMethod.invoke(getObject(cls), callbackProcessor);
    }

    private void invokeMethodParams1(Class cls, String method, Object[] params, CallbackProcessor callbackProcessor) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Method remoteMethod = cls.getMethod(method, params[0].getClass(), callbackProcessor.getClass());
        remoteMethod.invoke(getObject(cls), params[0], callbackProcessor);
    }

    private void invokeMethodParams2(Class cls, String method, Object[] params, CallbackProcessor callbackProcessor) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Method remoteMethod = cls.getMethod(method, params[0].getClass(), params[1].getClass(), callbackProcessor.getClass());
        remoteMethod.invoke(getObject(cls), params[0], params[1], callbackProcessor);
    }

    private void invokeMethodParams3(Class cls, String method, Object[] params, CallbackProcessor callbackProcessor) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Method remoteMethod = cls.getMethod(method, params[0].getClass(), params[1].getClass(), params[2].getClass(), callbackProcessor.getClass());
        remoteMethod.invoke(getObject(cls), params[0], params[1], params[2], callbackProcessor);
    }

    private void invokeMethodParams4(Class cls, String method, Object[] params, CallbackProcessor callbackProcessor) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Method remoteMethod = cls.getMethod(method, params[0].getClass(), params[1].getClass(), params[2].getClass(), params[3].getClass(), callbackProcessor.getClass());
        remoteMethod.invoke(getObject(cls), params[0], params[1], params[2], params[3], callbackProcessor);
    }

    private void invokeMethodParams5(Class cls, String method, Object[] params, CallbackProcessor callbackProcessor) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Method remoteMethod = cls.getMethod(method, params[0].getClass(), params[1].getClass(), params[2].getClass(), params[3].getClass(), params[4].getClass(), callbackProcessor.getClass());
        remoteMethod.invoke(getObject(cls), params[0], params[1], params[2], params[3], params[4], callbackProcessor);
    }

    private void invokeMethodParams6(Class cls, String method, Object[] params, CallbackProcessor callbackProcessor) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Method remoteMethod = cls.getMethod(method, params[0].getClass(), params[1].getClass(), params[2].getClass(), params[3].getClass(), params[4].getClass(), params[5].getClass(), callbackProcessor.getClass());
        remoteMethod.invoke(getObject(cls), params[0], params[1], params[2], params[3], params[4], params[5], callbackProcessor);
    }

    private void invokeMethodParams7(Class cls, String method, Object[] params, CallbackProcessor callbackProcessor) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Method remoteMethod = cls.getMethod(method, params[0].getClass(), params[1].getClass(), params[2].getClass(), params[3].getClass(), params[4].getClass(), params[5].getClass(), params[6].getClass(), callbackProcessor.getClass());
        remoteMethod.invoke(getObject(cls), params[0], params[1], params[2], params[3], params[4], params[5], params[6], callbackProcessor);
    }

    private void invokeMethodParams8(Class cls, String method, Object[] params, CallbackProcessor callbackProcessor) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Method remoteMethod = cls.getMethod(method, params[0].getClass(), params[1].getClass(), params[2].getClass(), params[3].getClass(), params[4].getClass(), params[5].getClass(), params[6].getClass(), params[7].getClass(), callbackProcessor.getClass());
        remoteMethod.invoke(getObject(cls), params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], callbackProcessor);
    }

    private void invokeMethodParams9(Class cls, String method, Object[] params, CallbackProcessor callbackProcessor) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Method remoteMethod = cls.getMethod(method, params[0].getClass(), params[1].getClass(), params[2].getClass(), params[3].getClass(), params[4].getClass(), params[5].getClass(), params[6].getClass(), params[7].getClass(), params[8].getClass(), callbackProcessor.getClass());
        remoteMethod.invoke(getObject(cls), params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], callbackProcessor);
    }

    private void invokeMethodParams10(Class cls, String method, Object[] params, CallbackProcessor callbackProcessor) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Method remoteMethod = cls.getMethod(method, params[0].getClass(), params[1].getClass(), params[2].getClass(), params[3].getClass(), params[4].getClass(), params[5].getClass(), params[6].getClass(), params[7].getClass(), params[8].getClass(), params[9].getClass(), callbackProcessor.getClass());
        remoteMethod.invoke(getObject(cls), params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], callbackProcessor);
    }
}
