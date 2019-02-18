package com.plug.plug2.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fanjun.processroute.callback.CallbackProcessor;

public class Test3Activity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CallbackProcessor<String> callbackProcessor = (CallbackProcessor<String>) getIntent().getSerializableExtra("call");
        callbackProcessor.callback("操作成功！123123");
    }
}
