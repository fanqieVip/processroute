// ServiceCallback.aidl
package com.plug.common.service;

// Declare any non-default types here with import statements

interface ServiceCallback {
    void callback(String callbackId, boolean success, String resJson, String resClsJson, String errorMsg);
}
