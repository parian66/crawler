package com.iranig.crawler.crawler.impl;

import com.iranig.crawler.crawler.Fetcher;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Contributors:
 * Hossein Amiri Parian <parian66@gmail.com>
 */
@Component
public class FetcherImpl implements Fetcher {

  @Autowired
  private OkHttpClient client;

  @Override
  public String fetch(String url) throws IOException {
    Response response = client.newCall(new Builder().url(url).get().build()).execute();
    return response.isSuccessful() ? response.body().string() : "";
  }
}
