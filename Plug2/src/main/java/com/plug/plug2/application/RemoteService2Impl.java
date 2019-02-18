package com.plug.plug2.application;

import android.content.Intent;

import com.fanjun.processroute.callback.CallbackProcessor;
import com.plug.common.application.BaseApplication;
import com.plug.common.plugservice.RemoteService2;
import com.plug.plug2.activitys.Test3Activity;

public class RemoteService2Impl implements RemoteService2 {
    @Override
    public void regist(CallbackProcessor<String> callbackProcessor, String tel, Integer flag) {
        Intent intent = new Intent(BaseApplication.application, Test3Activity.class);
        intent.putExtra("call", callbackProcessor);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.application.startActivity(intent);
    }
}
