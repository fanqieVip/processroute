
 // AidlRoute.aidl
package com.fanjun.processroute;
import com.fanjun.processroute.ServiceCallback;

interface AidlRoute {
    //remoteCls调用的远程方法类名
    //method 调用的远程方法名
    //paramsJson 调用的远程方法参数集合json字符串
    //paramClssJson 调用的远程方法参数类型集合json类型
    //paramsJson,paramClssJson位置一一对应，用于多进程访问时自动解析对象
    //callbackId回调标识id
    //processId 调用的client端的应用包名
    void onEvent(String remoteCls, String method, String paramsJson, String paramClssJson, String callbackId, String processId);
    //packageName 回调的client端应用包名
    //callback 回调接口
    void registCallback(String processId, ServiceCallback callback);
}

