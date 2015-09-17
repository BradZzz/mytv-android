package com.bradzzz.dotdashdot.mytv_stream.utils;

import android.util.Log;

import com.loopj.android.http.*;

public class ClassMyTVAPIController {
    private static String TAG = "ClassMyTVAPIController";
    private static Params params;
    class Params{
        private String base_url;
        Params(String base_url){
            this.base_url = base_url;
        }
        public String getBase () {
            return base_url;
        }
    }

    ClassMyTVAPIController(String base_url){
        params = new Params(base_url);
    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        Log.i(TAG, params.getBase());
        Log.i(TAG, relativeUrl);
        return params.getBase() + relativeUrl;
    }
}