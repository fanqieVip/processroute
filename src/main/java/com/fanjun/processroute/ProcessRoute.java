package com.fanjun.processroute;

import android.content.Context;
import android.text.TextUtils;

import com.fanjun.processroute.request.ConnectionProcessor;
import com.fanjun.processroute.request.RouteReq;

import java.util.HashMap;
import java.util.Map;

public class ProcessRoute {
    private static Map<String, ConnectionProcessor> serviceConnectionMap;

    public static void send(Context context, RouteReq routeReq) {
        if (serviceConnectionMap == null){
            serviceConnectionMap = new HashMap<>();
        }
        if (TextUtils.isEmpty(routeReq.getProcessId())){
            if (routeReq.getRouteListener() != null){
                routeReq.getRouteListener().fail("远程协议"+routeReq.getRemoteClass()+"未指明ProgressId");
            }
            return;
        }
        if (!TextUtils.isEmpty(routeReq.getMethod())) {
            ConnectionProcessor connectionProcessor = serviceConnectionMap.get(routeReq.getProcessId());
            if (connectionProcessor == null || connectionProcessor.isInterrupted()) {
                connectionProcessor = new ConnectionProcessor(context, routeReq);
                connectionProcessor.start();
            }
            connectionProcessor.connect(context, routeReq);
        }else{
            if (routeReq.getRouteListener() != null){
                routeReq.getRouteListener().fail("参数错误,methodName不能为空！");
            }
        }
    }
}
