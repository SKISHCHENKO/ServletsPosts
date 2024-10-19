package Net.service;

import Net.exception.NotFoundException;
import Net.model.Post;
import Net.repository.PostRepository;

import java.util.List;
import java.util.Optional;

public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    // Возвращает все посты
    public List<Post> all() {
        return repository.all();
    }

    // Возвращает пост по ID или выбрасывает исключение, если не найден
    public Post getById(long id) {
        return repository.getById(id)
                .orElseThrow(() -> new NotFoundException("Post with id " + id + " not found"));
    }

    // Сохраняет пост, если ID 0 - создает новый, иначе обновляет существующий
    // Если такого поста нет, выбрасывает исключение
    public Post save(Post post) {
        if (post.getId() == 0) {
            // Создание нового поста
            return repository.save(post);
        } else {
            // Обновление существующего поста
            Optional<Post> existingPost = repository.getById(post.getId());

            // Проверка, существует ли пост с таким ID
            if (existingPost.isEmpty()) {
                throw new NotFoundException("Post with id " + post.getId() + " not found");
            }

            // Проверка на потенциальный конфликт (например, если содержимое одинаково)
            if (existingPost.get().getContent().equals(post.getContent())) {
                throw new IllegalArgumentException("Conflict: Post with id " + post.getId() + " has the same content");
            }

            return repository.save(post); // Обновление поста
        }
    }

    // Удаляет пост по ID, если пост не найден, выбрасывает исключение
    public void removeById(long id) {
        Optional<Post> post = repository.getById(id);
        if (post.isEmpty()) {
            throw new NotFoundException("Post with id " + id + " not found");
        }
        repository.removeById(id);
    }
}

