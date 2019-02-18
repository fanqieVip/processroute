package com.plug.plug1.processors;

import com.fanjun.processroute.callback.CallbackProcessor;
import com.fanjun.processroute.processors.RemoteServiceProcessor;

import java.lang.reflect.Method;

public class LoginProcessor extends RemoteServiceProcessor {
    @Override
    public boolean process(Method method, CallbackProcessor callbackProcessor, Object... params) {
        return true;
    }
}
