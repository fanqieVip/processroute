package com.plug.plug1.activitys;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TextService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
