package com.plug.common.application;

import android.app.Application;

import com.fanjun.messagecenter.MessageCenter;
import com.fanjun.processroute.ProcessRoute;
import com.plug.common.BuildConfig;

public class BaseApplication extends Application {
    public static Application application;
    @Override
    public void onCreate() {
        super.onCreate();
        MessageCenter.create(this);
        application = this;
        ProcessRoute.ini(this, BuildConfig.isPlugMode);
        ProcessRoute.onApplicationCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ProcessRoute.onApplicationTerminate();
    }
}
