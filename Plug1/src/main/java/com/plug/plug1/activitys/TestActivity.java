package com.plug.plug1.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.fanjun.messagecenter.MessageCenter;
import com.fanjun.messagecenter.annotion.MHanderReceiveTag;
import com.fanjun.processroute.request.RouteListener;
import com.fanjun.processroute.request.RouteReq;
import com.plug.common.plugservice.impl.RemoteService2Impl;
import com.plug.plug1.R;

public class TestActivity extends AppCompatActivity {
    TextView mText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plug);
        MessageCenter.registMessageHandler(this);
        mText = findViewById(R.id.mText);
        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteReq.create(RemoteService2Impl.regist, "18966663256",123123)
                        .routeListener(new RouteListener<String>() {
                            @Override
                            public void callback(String obj) {
                                mText.setText(obj);
                            }

                            @Override
                            public void fail(String errorMsg) {
                                mText.setText(errorMsg);
                            }
                        }).send();
            }
        });
    }

    @MHanderReceiveTag("nihao")
    void mmm(String obj) {
        mText.setText(obj);
    }
}
