/*
 * Copyright (C) 2014 Pedro Vicente Gómez Sánchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bradzzz.dotdashdot.mytv_stream.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import com.bradzzz.dotdashdot.mytv_stream.myTVPanelApplication;

/**
 * Base FragmentActivity created to support dependency injection in activities extending this
 * Activity. This Activity extends from SherlockFragmentActivity to use the support Action Bar. The
 * implementation uses the application class to inject dependencies.
 */
public class DIFragmentActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((myTVPanelApplication) getApplication()).inject(this);
  }
}
