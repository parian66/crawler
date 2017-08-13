package com.iranig.crawler.crawler;

import com.google.common.collect.Queues;
import com.iranig.crawler.model.News;
import com.iranig.crawler.service.NewsService;
import java.io.IOException;
import java.util.Queue;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Contributors:
 * Hossein Amiri Parian <parian66@gmail.com>
 */
@Component
public class Crawler {

  private static final Logger logger = LoggerFactory.getLogger(Crawler.class);

  @Autowired
  private Fetcher fetcher;

  @Autowired
  private Parser parser;

  @Autowired
  private NewsService newsService;

  @Value("${crawler.homeUrl}")
  private String homeUrl;

  @Value("${crawler.newsUrl}")
  private String newsUrl;

  private Queue<Integer> newsIds = Queues.newConcurrentLinkedQueue();

  @PostConstruct
  public void init() {
    newsIds.addAll(newsService.findNotFetchIds());
  }

  @Scheduled(fixedRate = 60000)
  public void crawlHome() {
    try {
      newsIds.addAll(newsService.persistence(parser.pars(fetcher.fetch(homeUrl)))
          .stream()
          .filter(n -> n.getBody() == null)
          .map(News::getId)
          .collect(Collectors.toList()));
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Scheduled(fixedRate = 1000)
  public void crawlNews() {
    if (newsIds.isEmpty()) {
      logger.debug("newsIds is empty");
      return;
    }
    Integer newsId = newsIds.poll();
    try {
      newsIds.addAll(newsService.persistence(parser.pars(fetcher.fetch(newsUrl + newsId)))
          .stream()
          .filter(n -> n.getBody() == null)
          .map(News::getId)
          .collect(Collectors.toList()));
    } catch (IOException e) {
      logger.error("can not fetch news#", newsId, e);
    }
  }
}