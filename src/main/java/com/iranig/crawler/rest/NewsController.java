package com.iranig.crawler.rest;

import com.iranig.crawler.model.News;
import com.iranig.crawler.repository.NewsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contributors:
 * Hossein Amiri Parian <parian66@gmail.com>
 */
@RestController
@RequestMapping(value = "news", method = RequestMethod.GET)
public class NewsController {

  @Autowired
  private NewsRepository newsRepository;

  @RequestMapping
  List<News> list() {
    return list(1);
  }

  @RequestMapping(path = "{id}")
  public News get(@PathVariable Integer id) {
    return newsRepository.findOne(id);
  }

  @RequestMapping(path = "page/{page}")
  public List<News> list(@PathVariable Integer page) {
    if (page < 1) {
      throw new IllegalArgumentException("page must be >= 1");
    }
    PageRequest request = new PageRequest(page - 1, 10, Direction.DESC, "fetchedDate");
    return newsRepository.findAll(request).getContent();
  }
}
