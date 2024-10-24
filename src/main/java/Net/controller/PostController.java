package Net.controller;

import Net.exception.NotFoundException;
import com.google.gson.Gson;
import Net.model.Post;
import Net.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void all(HttpServletResponse response) throws IOException {
        System.out.println("Printing all posts");
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
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
    public void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var post = gson.fromJson(request.getReader(), Post.class);
        final var data = service.save(post);
        response.getWriter().print(gson.toJson(data));
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
    public String handleNotFoundException(NotFoundException e) {
        return "{\"error\":\"Post not found\"}";
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
