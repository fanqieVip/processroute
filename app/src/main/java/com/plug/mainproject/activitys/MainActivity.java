package com.plug.mainproject.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.fanjun.processroute.request.RouteListener;
import com.fanjun.processroute.request.RouteReq;
import com.plug.common.plugservice.impl.RemoteService2Impl;
import com.plug.mainproject.R;

public class MainActivity extends AppCompatActivity {
    TextView mText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = findViewById(R.id.mText);
        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteReq.create(RemoteService2Impl.regist, "18800000000", 12312313)
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
}
