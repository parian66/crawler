package com.iranig.crawler.repository;

import com.iranig.crawler.model.News;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Contributors:
 * Hossein Amiri Parian <parian66@gmail.com>
 */
public interface NewsRepository extends JpaRepository<News, Integer> {

  List<News> findByFetchedDateIsNullOrderByIdDesc();

  List<News> findByIdIn(Iterable<Integer> ids);
}
