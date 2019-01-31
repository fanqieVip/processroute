package com.fanjun.processroute.request;

import android.support.annotation.NonNull;

import com.fanjun.processroute.remote.ProcessId;
import com.fanjun.processroute.utils.JsonUtils;

import java.util.Arrays;

public class RouteReq {
    private String processId;
    private String method;
    private RouteListener routeListener;
    private Object[] params;
    private String[] paramClss;
    private Class remoteClass;

    private RouteReq(@NonNull Class remoteClass, @NonNull String method) {
        if (remoteClass.isAnnotationPresent(ProcessId.class)){
            ProcessId processId = (ProcessId) remoteClass.getAnnotation(ProcessId.class);
            this.processId = processId.value();
        }
        this.method = method;
        this.remoteClass = remoteClass;
    }

    /**
     * 创建路由请求
     *
     * @param remoteClass 请求进程的通讯协议类
     * @param method      请求进程的通讯协议的方法
     * @return
     */
    public static RouteReq build( @NonNull Class remoteClass, @NonNull String method) {
        return new RouteReq(remoteClass, method);
    }

    /**
     *
     * @param params
     * @return
     */
    public RouteReq params(@NonNull Object... params) {
        this.params = params;
        if (params != null) {
            paramClss = new String[params.length];
            for (int i = 0; i < params.length; i++) {
                Object object = params[i];
                if (object != null) {
                    paramClss[i] = object.getClass().getName();
                } else {
                    paramClss[i] = null;
                }
            }
        }
        return this;
    }

    public RouteReq routeListener(@NonNull RouteListener routeListener) {
        this.routeListener = routeListener;
        return this;
    }

    public String getProcessId() {
        return processId;
    }

    public String getMethod() {
        return method;
    }

    public Object[] getParams() {
        return params;
    }

    public String[] getParamClss() {
        return paramClss;
    }

    public String getParamsJson() {
        return JsonUtils.getInstance().toJson(params);
    }

    public String getParamClssJson() {
        return JsonUtils.getInstance().toJson(paramClss);
    }

    public RouteListener getRouteListener() {
        return routeListener;
    }

    public String getRemoteClass() {
        return remoteClass.getName();
    }

    @Override
    public String toString() {
        return "RouteReq{" +
                "processId='" + processId + '\'' +
                ", method='" + method + '\'' +
                ", params=" + Arrays.toString(params) +
                ", paramClss=" + Arrays.toString(paramClss) +
                ", remoteClass=" + remoteClass +
                '}';
    }
}
