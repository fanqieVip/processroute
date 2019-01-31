package com.fanjun.processroute.utils;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {
    private static JsonUtils jsonUtils;
    private Gson gson;
    public static synchronized JsonUtils getInstance(){
        if (jsonUtils == null){
            jsonUtils = new JsonUtils();
        }
        return jsonUtils;
    }

    private JsonUtils() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    public String toJson(@NonNull Object obj){
        return gson.toJson(obj);
    }

    public <T>T fromJson(@NonNull String json, @NonNull Class<T> cls){
        return gson.fromJson(json, cls);
    }
}
