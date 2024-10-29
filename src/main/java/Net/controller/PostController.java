package Net.controller;

import Net.model.Post;
import Net.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Post> all() {
        System.out.println("Printing all posts");
        return service.all();
    }
    // Получение поста по ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post getById(@PathVariable long id) {
        System.out.println("Printing post with ID: " + id);
        return service.getById(id);
    }
    // Сохранение нового поста или обновление существующего
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post save(@RequestBody Post post) {
        return service.save(post);
    }
    // Удаление поста по ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@PathVariable long id) {
        service.removeById(id);
        System.out.println("Deleted post with ID: " + id);
    }
        @GetMapping("/test")
        @ResponseStatus(HttpStatus.OK)
        public Map<String, String> test() {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Hello, World!");
            return response;
        }

}
