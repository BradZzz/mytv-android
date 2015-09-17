package com.bradzzz.dotdashdot.mytv_stream.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;

import com.bradzzz.dotdashdot.mytv_stream.R;
import com.bradzzz.dotdashdot.mytv_stream.utils.ClassMyTVClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ActivityHome extends Activity {
    private static String TAG = "ActivityHome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.iv_tv_shows) void openTvShowsSampleActivity() {
        Log.i(TAG, "Clicked!");
        Intent intent = new Intent(this, TvShowsActivity.class);
        startActivity(intent);
    }
}
