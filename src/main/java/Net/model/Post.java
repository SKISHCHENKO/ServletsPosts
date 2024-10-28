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
    this.setContent(content);  // Применение валидации
    this.removed = false;
  }

  public Post(long id, String content, boolean removed) {
    this.id = id;
    this.setContent(content);  // Применение валидации
    this.removed = removed;
  }

  public static Post createWithContent(String content) {
    return new Post(0, content, false);
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
    if (content == null || content.trim().isEmpty()) {
      throw new IllegalArgumentException("Content cannot be null or empty");
    }
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
  public Post markAsRemoved() {
    this.removed = true;
    return this;
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