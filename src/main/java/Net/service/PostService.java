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
        Optional<Post> postcheck = repository.getById(post.getId());
        if (postcheck.isEmpty()) {
            throw new NotFoundException("Post with id " + post.getId() + " not found");
        }
        return repository.save(post);
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

