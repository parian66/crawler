package com.iranig.crawler.crawler.impl;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

import com.iranig.crawler.crawler.Parser;
import com.iranig.crawler.model.News;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Contributors:
 * Hossein Amiri Parian <parian66@gmail.com>
 */
@Component
public class ParserImpl implements Parser {

  private static final Logger logger = LoggerFactory.getLogger(ParserImpl.class);

  @Override
  public List<News> pars(String html) {
    return pars(Jsoup.parse(html));
  }

  @Override
  public List<News> pars(InputStream html) {
    try {
      return pars(Jsoup.parse(html, "UTF-8", ""));
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
      return Collections.emptyList();
    }
  }

  private List<News> pars(Document doc) {
    final News currentNews = parsNews(doc);

    return doc.select("a[href^=/fa/news/]")
        .stream()
        .map(e -> {
          Integer newsId = valueOf(substringBetween(e.attr("href"), "/fa/news/", "/"));
          if (currentNews != null && currentNews.getId().equals(newsId)) {
            return currentNews;
          } else {
            News news = new News();
            news.setId(newsId);
            news.setTitle(e.attr("title"));
            return news;
          }
        })
        .collect(Collectors.toList());
  }

  private static News parsNews(Document doc) {
    News currentNews = null;
    if (!doc.select("meta[itemprop=name]").isEmpty()) {
      currentNews = new News();
      currentNews.setId(valueOf(substringAfter(getContent(doc, "meta[property=og:url]"), "news/")));
      currentNews.setFetchedDate(new Date());
      currentNews.setTitle(getContent(doc, "meta[property=og:title]"));
      currentNews.setCategory(getContent(doc, "meta[property=article:section]"));
      currentNews.setDescription(getContent(doc, "meta[name=description]"));
      currentNews.setKeywords(newHashSet(getContent(doc, "meta[name=keywords]").split("،")));
      currentNews.setBody(doc.select("div.body").html());
    }
    return currentNews;
  }

  private static String getContent(Element element, String selector) {
    return element.select(selector).attr("content");
  }
}
