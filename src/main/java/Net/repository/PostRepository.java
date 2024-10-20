package Net.repository;

import Net.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    // Хранилище для постов с потокобезопасностью
    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();

    private final AtomicLong idGenerator = new AtomicLong(0);

    // Получение всех постов
    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    // Получение поста по ID
    public Optional<Post> getById(long id) {
        // Возвращаем пост по ID, если он существует
        return Optional.ofNullable(posts.get(id));
    }

    // Сохранение поста
    public Post save(Post post) {
        if (post.getId() == 0) {
            long newId = idGenerator.incrementAndGet();
            post.setId(newId);
            posts.put(newId, post);
        } else {
            posts.put(post.getId(), post);
        }
        return post;
    }

    // Удаление поста по ID
    public void removeById(long id) {
        posts.remove(id);
    }
}