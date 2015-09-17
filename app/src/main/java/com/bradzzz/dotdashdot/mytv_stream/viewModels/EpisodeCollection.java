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

import com.pedrogomez.renderers.AdapteeCollection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * EpisodeCollection implementation used to contains all the episodes information for each
 * TvShowViewModel. This implementation is based on a LinkedList.
 */
public class EpisodeCollection implements AdapteeCollection<EpisodeViewModel> {

  private final List<EpisodeViewModel> episodes;

  public EpisodeCollection() {
    this.episodes = new LinkedList<EpisodeViewModel>();
  }

  /**
   * Return the number of episodes inside the collection.
   */
  @Override
  public int size() {
    return episodes.size();
  }

  /**
   * Return a episode obtained by position.
   */
  @Override
  public EpisodeViewModel get(int i) {
    return episodes.get(i);
  }

  /**
   * Add an episode to the collection.
   */
  @Override
  public boolean add(EpisodeViewModel episodeViewModel) {
    episodes.add(episodeViewModel);
    return false;
  }

  @Override
  public boolean remove(Object o) {
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends EpisodeViewModel> collection) {
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
