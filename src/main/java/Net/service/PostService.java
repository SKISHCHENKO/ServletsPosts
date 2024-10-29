package Net.service;

import Net.exception.NotFoundException;
import Net.model.Post;
import Net.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        Post post = repository.getById(id);
        if (post == null) {
            // Пост не найден
            System.out.println("NotFoundException is being thrown for ID: " + id);
            throw new NotFoundException("Post with id " + id + " not found");
        }
        if (post.isRemoved()) {
            // Пост найден, но помечен как удаленный
            System.out.println("NotFoundException is being thrown for ID: " + id);
            throw new NotFoundException("Post with id " + id + " has been removed");
        }
        return post; // Возвращаем пост, если он существует и не удален
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
        Post existingPost = repository.getById(postId);
        if (existingPost == null) {
            throw new NotFoundException("Post with id " + postId + " not found");
        }
        if (existingPost.isRemoved()) {
            throw new NotFoundException("Post with id " + postId + " has been removed");
        }
        // Проверка на потенциальный конфликт (например, если содержимое одинаково)
        if (existingPost.getContent().equals(post.getContent())) {
            throw new IllegalArgumentException("Conflict: Post with id " + postId + " has the same content");
        }
        return repository.save(post); // Обновление поста
    }

    // Мягко удаляет пост по ID, помечая его как удаленный. Если пост не найден, выбрасывает исключение.
    public void removeById(long id) {
        Post post = repository.getById(id);
        if (post == null) {
            throw new NotFoundException("Post with id " + id + " not found");
        }
        if (post.isRemoved()) {
            throw new NotFoundException("Post with id " + id + " has been removed");
        }
        post.markAsRemoved(); // Помечаем пост как удаленный
        repository.save(post); // Сохраняем изменения
    }
}

