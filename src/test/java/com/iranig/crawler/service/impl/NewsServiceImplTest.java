package com.iranig.crawler.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.iranig.crawler.BaseTest;
import com.iranig.crawler.model.News;
import com.iranig.crawler.repository.NewsRepository;
import com.iranig.crawler.service.NewsService;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Contributors:
 * Hossein Amiri Parian <parian66@gmail.com>
 */
public class NewsServiceImplTest extends BaseTest {

  @Autowired
  private NewsService newsService;

  @Autowired
  private NewsRepository newsRepository;

  @Before
  public void clear() {
    newsRepository.deleteAll();
  }

  @Test
  public void findNotFetchIds() throws Exception {
    Set<Integer> fetchedIds = Sets.newHashSet(2, 4, 6, 8, 10);
    Set<Integer> notFetchedIds = Sets.newHashSet(1, 3, 5, 7, 9);

    for (int i = 1; i <= 10; i++) {
      News news = new News();
      news.setId(i);
      if (fetchedIds.contains(i)) {
        news.setFetchedDate(new Date());
      }
      newsRepository.save(news);
    }

    assertTrue(notFetchedIds.containsAll(newsService.findNotFetchIds()));
  }

  @Test
  public void persistence() throws Exception {
    Set<Integer> updatedIds = Sets.newHashSet(2, 4, 6, 8, 10);

    List<News> newsList = Lists.newArrayList();
    for (int i = 1; i <= 10; i++) {
      News news = new News();
      news.setId(i);
      if (updatedIds.contains(i)) {
        news.setDescription("description#" + i);
        newsRepository.save(news);
      }
      news.setTitle("title#" + i);
      newsList.add(news);
    }

    List<News> list = newsService.persistence(newsList);
    for (News news : list) {
      assertNotNull(news.getTitle());
      if (updatedIds.contains(news.getId())) {
        assertNotNull(news.getDescription());
      } else {
        assertNull(news.getDescription());
      }
    }

  }
}