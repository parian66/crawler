package com.iranig.crawler.service;

import com.iranig.crawler.model.News;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * Contributors:
 * Hossein Amiri Parian <parian66@gmail.com>
 */
public interface NewsService {

  @Transactional(readOnly = true)
  List<Integer> findNotFetchIds();

  @Transactional
  List<News> persistence(List<News> newsList);
}
