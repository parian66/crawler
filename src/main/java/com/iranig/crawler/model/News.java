package com.iranig.crawler.model;

import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Contributors:
 * Hossein Amiri Parian <parian66@gmail.com>
 */
@Entity
@Table(indexes = {@Index(name = "idx_fetchedDate", columnList = "fetchedDate", unique = false)})
public class News {

  @Id
  private Integer id;

  private String title;

  @Lob
  @Column(columnDefinition = "TEXT")
  private String description;

  @Lob
  @Column(columnDefinition = "TEXT")
  private String body;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> keywords;

  private String category;

  private Date fetchedDate;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Set<String> getKeywords() {
    return keywords;
  }

  public void setKeywords(Set<String> keywords) {
    this.keywords = keywords;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Date getFetchedDate() {
    return fetchedDate;
  }

  public void setFetchedDate(Date fetchedDate) {
    this.fetchedDate = fetchedDate;
  }

  public void merge(News n) {
    id = n.id != null ? n.id : id;
    fetchedDate = n.fetchedDate != null ? n.fetchedDate : fetchedDate;
    keywords = n.keywords != null && !n.keywords.isEmpty() ? n.keywords : keywords;

    body = n.body != null && !n.body.isEmpty() ? n.body : body;
    title = n.title != null && !n.toString().isEmpty() ? n.title : title;
    category = n.category != null && !n.category.isEmpty() ? n.category : category;
    description = n.description != null && !n.description.isEmpty() ? n.description : description;
  }
}
