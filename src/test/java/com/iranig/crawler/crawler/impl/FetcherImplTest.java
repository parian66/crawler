package com.iranig.crawler.crawler.impl;

import com.iranig.crawler.BaseTest;
import com.iranig.crawler.crawler.Fetcher;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Contributors:
 * Hossein Amiri Parian <parian66@gmail.com>
 */
public class FetcherImplTest extends BaseTest {

  @Autowired
  private Fetcher fetcher;

  @Test
  public void fetch() throws Exception {
    Assert.assertFalse(fetcher.fetch("http://www.tabnak.ir/").isEmpty());
  }
}