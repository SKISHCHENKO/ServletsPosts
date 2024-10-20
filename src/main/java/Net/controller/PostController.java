package Net.controller;

import Net.exception.NotFoundException;
import com.google.gson.Gson;
import Net.model.Post;
import Net.service.PostService;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@Controller
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        final var gson = new Gson();
        System.out.println("Printing all posts");
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();

        try {
            Post post = service.getById(id);
            System.out.println("Printing post with ID: " + id);
            response.getWriter().print(gson.toJson(post));
        } catch (NotFoundException e) {
            // Если пост не найден, возвращаем статус 404
            handleError(response, "Post not found", HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            // Обработка других исключений (например, ошибок сервера)
            handleError(response, "Internal server error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        final var savedPost = service.save(post);
        System.out.println("Saving post with ID: " + savedPost.getId());
        response.getWriter().print(gson.toJson(savedPost));
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        try {
            service.removeById(id);
            System.out.println("Deleted post with ID: " + id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);  // Успешное удаление
        } catch (NotFoundException e) {
            // Если пост не найден, возвращаем статус 404
            handleError(response, "Post not found", HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            // Обработка других исключений
            handleError(response, "Internal server error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleError(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setContentType(APPLICATION_JSON);
        response.setStatus(statusCode);
        response.getWriter().write("{\"error\":\"" + message + "\"}");
    }
}