package Net.service;

import Net.exception.NotFoundException;
import Net.model.Post;
import Net.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    // Возвращает все посты, исключая помеченные как удаленные.
    public List<Post> all() {
        return repository.all().stream()
                .filter(post -> !post.isRemoved())
                .collect(Collectors.toList());
    }

    // Возвращает пост по ID или выбрасывает исключение, если пост не найден или удален.
    public Post getById(long id) {
        return repository.getById(id)
                .filter(post -> !post.isRemoved())
                .orElseThrow(() -> new NotFoundException("Post with id " + id + " not found or has been removed"));
    }

    // Сохраняет пост, если ID 0 - создает новый, иначе обновляет существующий
    // Если такого поста нет, выбрасывает исключение
    public Post save(Post post) {
        long postId = post.getId();
        // Создание нового поста
        if (postId == 0) {
            return repository.save(post);
        }
        // Обновление существующего поста
        Post existingPost = repository.getById(postId)
                .filter(p -> !p.isRemoved())
                .orElseThrow(() -> new NotFoundException("Post with id " + postId + " not found or has been removed"));

        // Проверка на потенциальный конфликт (например, если содержимое одинаково)
        if (existingPost.getContent().equals(post.getContent())) {
            throw new IllegalArgumentException("Conflict: Post with id " + postId + " has the same content");
        }

        return repository.save(post); // Обновление поста
    }

    // Мягко удаляет пост по ID, помечая его как удаленный. Если пост не найден, выбрасывает исключение.
    public void removeById(long id) {
        Post post = repository.getById(id)
                .orElseThrow(() -> new NotFoundException("Post with id " + id + " not found"));
        post.markAsRemoved(); // Помечаем пост как удаленный
        repository.save(post); // Сохраняем изменения
    }
}

