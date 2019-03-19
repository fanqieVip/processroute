package com.plug.mainproject.application;

import android.app.Application;
import android.content.res.Configuration;

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

    @Override
    public void onConfigurationChanged(Application application, Configuration newConfig) {

    }

    @Override
    public void onLowMemory(Application application) {

    }

    @Override
    public void onTrimMemory(Application application, int level) {

    }
}
