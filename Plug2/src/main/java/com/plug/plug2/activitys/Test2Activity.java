package com.plug.plug2.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.fanjun.messagecenter.MessageCenter;
import com.fanjun.messagecenter.annotion.MHanderReceiveTag;
import com.fanjun.processroute.request.RouteListener;
import com.fanjun.processroute.request.RouteReq;
import com.plug.common.plugservice.impl.RemoteServiceImpl;
import com.plug.plug2.R;
public class Test2Activity extends AppCompatActivity {
    TextView mText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MessageCenter.registMessageHandler(this);
        setContentView(R.layout.activity_plug);
        mText = findViewById(R.id.mText);
        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteReq.create(RemoteServiceImpl.login, "18012331111", "password")
                        .routeListener(new RouteListener<String>() {
                            @Override
                            public void callback(String obj) {
                                mText.setText(obj);
                            }
                        }).send();
            }
        });
    }
    @MHanderReceiveTag("nihao")
    void mmm(String obj){
        mText.setText(obj);
    }
}
