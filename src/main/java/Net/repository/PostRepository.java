package Net.repository;

import Net.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
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
    public Post getById(long id) {
        // Возвращаем пост по ID, если он существует
        return posts.get(id);
    }

    // Сохранение поста
    public Post save(Post post) {
        if (post.getId() == 0) {
            long newId = idGenerator.incrementAndGet();
            post.setId(newId);
            posts.put(newId, post);
            System.out.println("Created new post with ID: " + newId);
        } else {
            posts.put(post.getId(), post);
            System.out.println("Updated post with ID: " + post.getId());
        }
        return post;
    }

    // Удаление поста по ID
    public void removeById(long id) {
        posts.remove(id);
        System.out.println("Removed post with ID: " + id);
    }
}