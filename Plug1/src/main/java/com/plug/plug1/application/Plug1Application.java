package com.plug.plug1.application;

import android.app.Application;

import com.fanjun.processroute.annotation.RegistApplication;
import com.fanjun.processroute.application.ApplicationDelegate;

@RegistApplication
public class Plug1Application implements ApplicationDelegate {
    @Override
    public void onCreate(Application application) {
    }

    @Override
    public void onTerminate(Application application) {

    }
}
