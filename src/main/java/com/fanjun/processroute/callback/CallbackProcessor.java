package com.fanjun.processroute.callback;

import android.os.RemoteException;

import com.fanjun.processroute.AidlRouteService;
import com.fanjun.processroute.ServiceCallback;

import java.io.Serializable;

public class CallbackProcessor<T> implements Serializable {
    private CallbackEntity callbackEntity;
    public CallbackProcessor( CallbackEntity callbackEntity){
        this.callbackEntity = callbackEntity;
    }
    public void callback(T data){
        callbackEntity.setData(data);
        ServiceCallback serviceCallback = AidlRouteService.callbackMap.get(callbackEntity.getFromPackageName());
        if (serviceCallback != null){
            try {
                serviceCallback.callback(callbackEntity.getCallbackId(), true, callbackEntity.getDataJson(), callbackEntity.getDataCls(), null);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void callFail(String error){
        ServiceCallback serviceCallback = AidlRouteService.callbackMap.get(callbackEntity.getFromPackageName());
        if (serviceCallback != null){
            try {
                serviceCallback.callback(callbackEntity.getCallbackId(), false, null, null, error);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public CallbackEntity getCallbackEntity() {
        return callbackEntity;
    }
}
