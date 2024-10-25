package Net.model;

import java.util.Objects;

public class Post {
  private long id;
  private String content;
  private boolean removed;

  public Post() {
    this.removed = false;
  }

  public Post(long id, String content) {
    this.id = id;
    this.content = content;
    this.removed = false;
  }

  public Post(long id, String content, boolean removed) {
    this.id = id;
    this.content = content;
    this.removed = removed;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean isRemoved() {
    return removed;
  }

  public void setRemoved(boolean removed) {
    this.removed = removed;
  }

  /**
   * Помечает пост как удаленный.
   */
  public void markAsRemoved() {
    this.removed = true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Post post = (Post) o;
    return id == post.id && removed == post.removed && Objects.equals(content, post.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, content, removed);
  }

  @Override
  public String toString() {
    return "Post{" +
            "id=" + id +
            ", content='" + content + '\'' +
            ", removed=" + removed +
            '}';
  }
}