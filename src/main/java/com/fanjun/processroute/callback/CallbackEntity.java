package com.fanjun.processroute.callback;

import com.fanjun.processroute.utils.JsonUtils;

import java.io.Serializable;

public class CallbackEntity<T> implements Serializable {
    private String callbackId;
    private String fromPackageName;
    private T data;
    private String dataCls;
    public String getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }

    public String getFromPackageName() {
        return fromPackageName;
    }

    public void setFromPackageName(String fromPackageName) {
        this.fromPackageName = fromPackageName;
    }

    public T getData() {
        return data;
    }

    public String getDataJson(){
        if (data != null){
            return JsonUtils.getInstance().toJson(data);
        }
        return null;
    }

    public String getDataCls() {
        return dataCls;
    }

    public void setDataCls(String dataCls) {
        this.dataCls = dataCls;
    }

    public void setData(T data) {
        this.data = data;
        if (data != null){
            dataCls = data.getClass().getName();
        }
    }

}
