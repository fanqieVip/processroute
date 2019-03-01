package com.plug.common.application;

import android.app.Application;
import android.content.res.Configuration;

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
        ProcessRoute.ini(this, BuildConfig.runAsApk);
        ProcessRoute.onApplicationCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ProcessRoute.onApplicationTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ProcessRoute.onApplicationConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ProcessRoute.onApplicationLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ProcessRoute.onApplicationTrimMemory(level);
    }
}
