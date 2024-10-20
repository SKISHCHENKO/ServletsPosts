package Net.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import Net.controller.PostController;
import Net.repository.PostRepository;
import Net.service.PostService;

@Configuration
public class JavaConfig {
    @Bean
    // аргумент метода и есть DI
    // название метода - название бина
    public PostController postController(PostService service) {
        return new PostController(service);
    }

    @Bean
    public PostService postService(PostRepository repository) {
        return new PostService(repository);
    }

    @Bean
    public PostRepository postRepository() {
        return new PostRepository();
    }
}