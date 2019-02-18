package com.plug.mainproject.application;

import android.app.Application;

import com.fanjun.processroute.annotation.RegistApplication;
import com.fanjun.processroute.application.ApplicationDelegate;
@RegistApplication
public class MainApplication implements ApplicationDelegate {
    @Override
    public void onCreate(Application application) {
    }

    @Override
    public void onTerminate(Application application) {

    }
}
