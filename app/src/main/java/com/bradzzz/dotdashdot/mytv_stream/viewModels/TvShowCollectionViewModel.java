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
package com.bradzzz.dotdashdot.mytv_stream.viewModels;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bradzzz.dotdashdot.mytv_stream.myTVPanelApplication;
import com.bradzzz.dotdashdot.mytv_stream.utils.ClassMyTVClient;
import com.pedrogomez.renderers.AdapteeCollection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * TvShowCollectionViewModel implementation used to contains all the tv shows information. This
 * implementation is based on a LinkedList with hardcoded data.
 */
public class TvShowCollectionViewModel implements AdapteeCollection<TvShowViewModel> {

  private static String TAG = "TvShowCollectionViewModel";

  private final List<TvShowViewModel> tvShows;

  @Inject public TvShowCollectionViewModel(List<TvShowViewModel> tvShows) {
    this.tvShows = tvShows;
  }

  /**
   * Return the number of tv shows inside the collection.
   */
  @Override
  public int size() {
    return tvShows.size();
  }

  /**
   * Return a TvShowViewModel obtained by position.
   */
  @Override
  public TvShowViewModel get(int i) {
    return tvShows.get(i);
  }

  /**
   * Add a TvShowViewModel to the collection.
   */
  @Override
  public boolean add(TvShowViewModel tvShowViewModel) {
    tvShows.add(tvShowViewModel);
    return false;
  }

  @Override
  public boolean remove(Object o) {
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends TvShowViewModel> collection) {
    return false;
  }

  @Override
  public boolean removeAll(Collection<?> collection) {
    return false;
  }

  @Override
  public void clear() {

  }
}
