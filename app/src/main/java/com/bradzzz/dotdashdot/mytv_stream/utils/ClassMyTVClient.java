package com.bradzzz.dotdashdot.mytv_stream.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bradley on 9/16/2015.
 */
public class ClassMyTVClient {

    //https://mytv-stream.herokuapp.com
    ///api/v1/cast/get/media

    private static String TAG = "ClassMyTVClient";
    private static Handler callback;
    public static String MEDIA_BASE = "/api/v1/cast/get/media";

    ClassMyTVAPIController controller;
    public ClassMyTVClient(String base, Handler callback) {
        this.callback = callback;
        controller = new ClassMyTVAPIController(base);
    }

    public void getMediaMeta() throws JSONException {
        controller.get(MEDIA_BASE, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                saveAndNotify(response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                saveAndNotify(response.toString());
            }
        });
    }

    public void saveAndNotify(String response){
        Message msgObj = callback.obtainMessage();
        Bundle b = new Bundle();
        b.putString("type", "response");
        b.putString("data", response);
        msgObj.setData(b);
        callback.sendMessage(msgObj);
    }
}
