package Net.servlet;

import Net.controller.PostController;
import Net.repository.PostRepository;
import Net.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MainServlet extends HttpServlet {
    private PostController controller;
    private final String PATH = "/api/posts/";
    private final String GET = "GET";
    private final String POST = "POST";
    private final String DELETE = "DELETE";

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final var path = req.getRequestURI();
        final var method = req.getMethod();
        try {
            switch (method) {
                case GET:
                    handleGetRequest(path, resp);
                    break;
                case POST:
                    handlePostRequest(req, resp);
                    break;
                case DELETE:
                    handleDeleteRequest(path, resp);
                    break;
                default:
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleGetRequest(String path, HttpServletResponse resp) throws IOException {
        if (path.equals(PATH)) {
            controller.all(resp);
        } else if (path.matches(PATH + "\\d+")) {
            final var id = extractId(path);
            if (id == 99999) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            controller.getById(id, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handlePostRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getRequestURI().equals(PATH)) {
            controller.save(req.getReader(), resp);

        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleDeleteRequest(String path, HttpServletResponse resp) throws IOException {
        if (path.matches(PATH + "\\d+")) {
            final var id = extractId(path);
            if (id == 99999) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            controller.removeById(id, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private long extractId(String path) {
        try {
            return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        } catch (NumberFormatException e) {
            return 99999; // Если ID некорректен
        }
    }
}

