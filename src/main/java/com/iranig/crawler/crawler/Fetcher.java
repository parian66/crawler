package com.iranig.crawler.crawler;

import java.io.IOException;

/**
 * Contributors:
 * Hossein Amiri Parian <parian66@gmail.com>
 */
public interface Fetcher {

  String fetch(String url) throws IOException;
}
