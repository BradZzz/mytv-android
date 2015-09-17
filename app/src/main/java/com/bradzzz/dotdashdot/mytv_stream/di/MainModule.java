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
package com.bradzzz.dotdashdot.mytv_stream.di;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;

import com.bradzzz.dotdashdot.mytv_stream.activity.TvShowsActivity;
import com.bradzzz.dotdashdot.mytv_stream.myTVPanelApplication;
import com.bradzzz.dotdashdot.mytv_stream.renderer.TvShowRenderer;
import com.bradzzz.dotdashdot.mytv_stream.renderer.rendererbuilder.TvShowCollectionRendererBuilder;
import com.bradzzz.dotdashdot.mytv_stream.viewModels.TvShowCollectionViewModel;
import com.bradzzz.dotdashdot.mytv_stream.viewModels.TvShowViewModel;
import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererAdapter;

import java.util.LinkedList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * MainModule created to provide the most important dependencies for this sample project
 */
@Module(injects = {TvShowsActivity.class, myTVPanelApplication.class}) public class MainModule {

  private final Application application;

  public MainModule(Application application) {
    this.application = application;
  }

  /**
   * Provisioning of a LayoutInflater instance obtained from the application context.
   */
  @Provides protected LayoutInflater provideLayoutInflater() {
    return LayoutInflater.from(application.getBaseContext());
  }

  /**
   * Provisioning of the base context obtained from the application.
   */
  @Provides protected Context provideContext() {
    return this.application.getBaseContext();
  }

  /**
   * Provisioning of a RendererBuilder implementation to work with tv shows ListView. More
   * information in this library: {@link https://github.com/pedrovgs/Renderers}
   */
  @Provides protected TvShowCollectionRendererBuilder provideTvShowCollectionRendererBuilder(
          Context context) {
    List<Renderer<TvShowViewModel>> prototypes = new LinkedList<Renderer<TvShowViewModel>>();
    prototypes.add(new TvShowRenderer(context));
    return new TvShowCollectionRendererBuilder(prototypes);
  }

  /**
   * Provisioning of a RendererAdapter implementation to work with tv shows ListView. More
   * information in this library: {@link https://github.com/pedrovgs/Renderers}
   */
  /*@Provides protected RendererAdapter<TvShowViewModel> provideTvShowRendererAdapter(
          LayoutInflater layoutInflater,
          TvShowCollectionRendererBuilder tvShowCollectionRendererBuilder,
          TvShowCollectionViewModel tvShowCollectionViewModel) {
    return new RendererAdapter<TvShowViewModel>(layoutInflater, tvShowCollectionRendererBuilder,
            tvShowCollectionViewModel);
  }*/
}
