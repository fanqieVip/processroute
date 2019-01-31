package com.fanjun.processroute.request;

public abstract class RouteListener<T> {
    /**
     * 准备中
     */
    public void prepare(){};
    /**
     * 回调
     */
    public abstract void callback(T obj);
    /**
     * 操作失败
     * @param errorMsg 错误信息
     */
    public void fail(String errorMsg){};
}
