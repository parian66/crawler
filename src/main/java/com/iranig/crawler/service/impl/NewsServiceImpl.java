package com.iranig.crawler.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iranig.crawler.model.News;
import com.iranig.crawler.repository.NewsRepository;
import com.iranig.crawler.service.NewsService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Contributors:
 * Hossein Amiri Parian <parian66@gmail.com>
 */
@Component
public class NewsServiceImpl implements NewsService {

  @Autowired
  private NewsRepository newsRepository;

  @Override
  public List<Integer> findNotFetchIds() {
    return newsRepository.findByFetchedDateIsNullOrderByIdDesc()
        .stream()
        .map(News::getId)
        .collect(Collectors.toList());
  }

  @Override
  public List<News> persistence(List<News> newsList) {
    Map<Integer, News> map = Maps.newHashMap();
    newsList.forEach(n -> map.put(n.getId(), n));

    newsRepository.findByIdIn(map.keySet()).forEach(n -> map.get(n.getId()).merge(n));
    return Lists.newArrayList(newsRepository.save(map.values()));
  }
}
