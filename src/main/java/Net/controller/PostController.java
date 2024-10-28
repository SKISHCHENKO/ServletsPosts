package Net.controller;

import Net.exception.NotFoundException;
import Net.model.Post;
import Net.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    // Обработка исключения NotFoundException (404)
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException e) {
        ResponseEntity<Map<String, String>> response = ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Post not found"));
        return response;
    }
    // Обработка IllegalArgumentException (400 Bad Request)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return "{\"error\":\"Bad Request\"}";
    }
    // Обработка других исключений (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        return "{\"error\":\"Internal server error\"}";
    }
}
