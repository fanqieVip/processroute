package com.plug.plug2.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fanjun.processroute.callback.CallbackProcessor;
import com.plug.plug2.R_;

import morexcess.chengnuovax.easyanontion.simpleannotion.AnnotionInit;
import morexcess.chengnuovax.easyanontion.simpleannotion.annotion.EActivity;
import morexcess.chengnuovax.easyanontion.simpleannotion.annotion.ViewById;

@EActivity(R_.layout.activity_plug)
public class Test3Activity extends AppCompatActivity {
    @ViewById
    TextView mText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnnotionInit.getInstance().init(this);
        mText.setText("操作成功！123123");
        CallbackProcessor<String> callbackProcessor = (CallbackProcessor<String>) getIntent().getSerializableExtra("call");
        callbackProcessor.callback("操作成功！123123");

    }
}
