package com.iranig.crawler.crawler.impl;

import com.google.common.io.Resources;
import com.iranig.crawler.BaseTest;
import com.iranig.crawler.crawler.Parser;
import com.iranig.crawler.model.News;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Contributors:
 * Hossein Amiri Parian <parian66@gmail.com>
 */
public class ParserImplTest extends BaseTest {

  @Autowired
  private Parser parser;

  @Test
  public void test_home() throws IOException {
    List<News> list = parser.pars(loadHtml("html/home.html"));
    Assert.assertFalse(list.isEmpty());
    for (News news : list) {
      Assert.assertNotNull(news.getId());
      Assert.assertNotNull(news.getTitle());
    }
  }

  @Test
  public void test_news() throws IOException {
    int newsId = 720118;
    List<News> list = parser.pars(loadHtml("html/" + newsId + ".html"));
    Assert.assertFalse(list.isEmpty());
    for (News news : list) {
      Assert.assertNotNull(news.getId());
      Assert.assertNotNull(news.getTitle());

      if (news.getId() == newsId) {
        Assert.assertNotNull(news.getTitle());
        Assert.assertNotNull(news.getBody());
        Assert.assertNotNull(news.getCategory());
        Assert.assertNotNull(news.getDescription());
        Assert.assertNotNull(news.getKeywords());
      }
    }
  }

  private InputStream loadHtml(String path) throws IOException {
    return Resources.getResource(path).openStream();
  }
}