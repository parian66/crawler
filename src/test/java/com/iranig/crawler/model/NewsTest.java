package com.iranig.crawler.model;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

/**
 * Contributors:
 * Hossein Amiri Parian <parian66@gmail.com>
 */
public class NewsTest {

  @Test
  public void merge() throws Exception {
    News old = new News();
    old.setId(1);
    old.setBody("body");
    old.setTitle("title");

    News updated = new News();
    updated.setKeywords(Sets.newHashSet());
    updated.setCategory("category");
    updated.setDescription("description");

    old.merge(updated);

    Assert.assertNotNull(old.getId());
    Assert.assertNotNull(old.getBody());
    Assert.assertNotNull(old.getTitle());
    Assert.assertNotNull(old.getKeywords());
    Assert.assertNotNull(old.getCategory());
    Assert.assertNotNull(old.getDescription());
  }
}