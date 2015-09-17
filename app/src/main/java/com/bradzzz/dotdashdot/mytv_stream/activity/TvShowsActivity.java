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

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bradzzz.dotdashdot.mytv_stream.R;
import com.bradzzz.dotdashdot.mytv_stream.renderer.EpisodeRenderer;
import com.bradzzz.dotdashdot.mytv_stream.renderer.EpisodeRendererAdapter;
import com.bradzzz.dotdashdot.mytv_stream.renderer.rendererbuilder.EpisodeRendererBuilder;
import com.bradzzz.dotdashdot.mytv_stream.renderer.rendererbuilder.TvShowCollectionRendererBuilder;
import com.bradzzz.dotdashdot.mytv_stream.utils.ClassMyTVClient;
import com.bradzzz.dotdashdot.mytv_stream.viewModels.EpisodeViewModel;
import com.bradzzz.dotdashdot.mytv_stream.viewModels.TvShowCollectionViewModel;
import com.bradzzz.dotdashdot.mytv_stream.viewModels.TvShowViewModel;
import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggableView;
import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Sample activity created to show a list of famous tv shows. If the user clicks on any list
 * element this sample shows a detailed draggable view with a picture and a list of episodes.
 */
public class TvShowsActivity extends DIFragmentActivity {

  private static final int DELAY_MILLIS = 10;

  @Inject LayoutInflater inflator;
  @Inject TvShowCollectionRendererBuilder builder;
  @InjectView(R.id.gv_tv_shows) GridView tvShowsGridView;
  @InjectView(R.id.iv_fan_art) ImageView fanArtImageView;
  @InjectView(R.id.lv_episodes) ListView episodesListView;
  @InjectView(R.id.draggable_view) DraggableView draggableView;

  Handler requestCallback = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      String message_type = msg.getData().getString("type");
      if (message_type.equals("response")) {
        List<TvShowViewModel> tvShows = new LinkedList<TvShowViewModel>();
        JSONArray req = null;
        TvShowViewModel tvShow = null;
        try {
          req = new JSONArray(msg.getData().getString("data"));
        } catch (Exception e) {
          Log.e(TAG, e.toString());
        }
        for (int i = 0; i < req.length(); i++) {
          try {
            JSONObject media = req.getJSONObject(i);
            Log.i(TAG, media.toString());
            if (media.get("type").toString().equals("tv")) {
              Log.i(TAG, "tv");
              Log.i(TAG, media.toString());
              tvShow = new TvShowViewModel(media.get("name").toString(), media.get("poster").toString(),
                      media.get("poster").toString(), 1); //This is the tab image. It's going to have to be the poster for now
              JSONArray ep = new JSONArray(media.get("episodes").toString());
              //JSONArray ep = .get("episodes");
              for (int k = 0; k < ep.length(); k++) {
                Log.i(TAG,ep.get(k).toString());
                tvShow.addEpisode(new EpisodeViewModel(ep.get(k).toString(), "?"));
              }
              tvShows.add(tvShow);
              Log.i(TAG, "Show added!");
            }
          } catch (Exception e){
            Log.e(TAG,e.toString());
          }
        }

        adapter = new RendererAdapter<TvShowViewModel>(inflator, builder, new TvShowCollectionViewModel(tvShows));
        init();
      }
    }
  };

  TextView header;
  RendererAdapter<TvShowViewModel> adapter;
  Context self;

  private TvShowViewModel tvShowSelected;

  private static String TAG = "TvShowsActivity";
  Handler handler = new Handler();
  Runnable runnable = new Runnable() {
    @Override
    public void run() {
      Log.i(TAG, "DING!");
      adapter.notifyDataSetChanged();
    }
  };

  /**
   * Initialize the Activity with some injected data.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tv_shows_sample);
    ButterKnife.inject(this);
    //handler.postDelayed(runnable, 10000);
    self = this;
    ClassMyTVClient client = new ClassMyTVClient("https://mytv-stream.herokuapp.com", requestCallback);
    try {
      client.getMediaMeta();
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void init(){
    initializeDraggableView();
    initializeGridView();
    hookListeners();
  }

  /**
   * Method triggered when the iv_fan_art widget is clicked. This method shows a toast with the tv
   * show selected.
   */
  @OnClick(R.id.iv_fan_art) void onFanArtClicked() {
    Toast.makeText(this, tvShowSelected.getTitle(), Toast.LENGTH_LONG).show();
  }

  /**
   * Initialize DraggableView.
   */
  private void initializeDraggableView() {
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        draggableView.setVisibility(View.GONE);
        draggableView.closeToRight();
      }
    }, DELAY_MILLIS);
  }

  /**
   * Initialize GridView with some injected data and configure OnItemClickListener.
   */
  private void initializeGridView() {
    tvShowsGridView.setAdapter(adapter);
    tvShowsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position,
          long id) {
        TvShowViewModel tvShow = adapter.getItem(position);
        tvShowSelected = tvShow;
        Picasso.with(getBaseContext())
            .load(tvShow.getFanArt())
            .placeholder(R.drawable.tv_show_placeholder)
            .into(fanArtImageView);
        renderEpisodesHeader(tvShow);
        renderEpisodes(tvShow);
        draggableView.setVisibility(View.VISIBLE);
        draggableView.maximize();
      }
    });
  }

  /**
   * Hook DraggableListener to draggableView to modify action bar title with the tv show
   * information.
   */
  private void hookListeners() {
    draggableView.setDraggableListener(new DraggableListener() {
      @Override
      public void onMaximized() {
        updateActionBarTitle();
      }

      @Override
      public void onMinimized() {
        updateActionBarTitle();
      }

      @Override
      public void onClosedToLeft() {
        resetActionBarTitle();
      }

      @Override
      public void onClosedToRight() {
        resetActionBarTitle();
      }
    });
  }

  /**
   * Update action bar title with the default title value.
   */
  private void resetActionBarTitle() {
    tvShowSelected = null;
    getSupportActionBar().setTitle(R.string.tv_shows_sample_activity_title);
  }

  /**
   * Update action bar title with the tv show selected title.
   */
  private void updateActionBarTitle() {
    if (tvShowSelected != null) {
      getSupportActionBar().setTitle(tvShowSelected.getTitle());
    }
  }

  /**
   * Render a list of episodes using a tv show view model with the information. This method create
   * an adapter with the episodes information to be inserted in the ListView.
   *
   * @param tvShow to render
   */
  private void renderEpisodes(final TvShowViewModel tvShow) {
    List<Renderer<EpisodeViewModel>> episodeRenderers = new LinkedList<Renderer<EpisodeViewModel>>();
    episodeRenderers.add(new EpisodeRenderer());
    EpisodeRendererBuilder episodeRendererBuilder = new EpisodeRendererBuilder(episodeRenderers);
    EpisodeRendererAdapter episodesAdapter = new EpisodeRendererAdapter(getLayoutInflater(), episodeRendererBuilder,
            tvShow.getEpisodes());
    episodesListView.setAdapter(episodesAdapter);
  }

  /**
   * Configure a view as episodes ListView header with the name of the tv show and the season.
   */
  private void renderEpisodesHeader(TvShowViewModel tvShow) {
    episodesListView.removeHeaderView(header);
    header = (TextView) getLayoutInflater().inflate(R.layout.episode_header, null);
    header.setText(tvShow.getTitle().toUpperCase() + " - SEASON 1");
    episodesListView.setAdapter(null);
    episodesListView.addHeaderView(header);
    episodesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position,
          long id) {
        if (tvShowSelected != null) {
          if (position > 0) {
            EpisodeViewModel episodeViewModel = tvShowSelected.getEpisodes().get(position - 1);
            Toast.makeText(getBaseContext(),
                    tvShowSelected.getTitle() + " - " + episodeViewModel.getTitle(), Toast.LENGTH_LONG)
                .show();
          }
        }
      }
    });
  }
}