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
import com.plug.plug2.R_;

import morexcess.chengnuovax.easyanontion.simpleannotion.AnnotionInit;
import morexcess.chengnuovax.easyanontion.simpleannotion.annotion.Click;
import morexcess.chengnuovax.easyanontion.simpleannotion.annotion.EActivity;
import morexcess.chengnuovax.easyanontion.simpleannotion.annotion.ViewById;

@EActivity(R_.layout.activity_plug)
public class Test2Activity extends AppCompatActivity {
    @ViewById
    TextView mText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MessageCenter.registMessageHandler(this);
        AnnotionInit.getInstance().init(this);
    }
    @Click
    void mText(){
        RouteReq.create(RemoteServiceImpl.login, "18012331111", "password")
                .routeListener(new RouteListener<String>() {
                    @Override
                    public void callback(String obj) {
                        mText.setText(obj);
                    }
                }).send();
    }
    @MHanderReceiveTag("nihao")
    void mmm(String obj){
        mText.setText(obj);
    }
}
