package com.iranig.crawler.crawler;

import com.iranig.crawler.model.News;
import java.io.InputStream;
import java.util.List;

/**
 * Contributors:
 * Hossein Amiri Parian <parian66@gmail.com>
 */
public interface Parser {

  List<News> pars(String html);

  List<News> pars(InputStream html);
}
