package org.example.controller;

import com.google.gson.Gson;
import org.example.model.Post;
import org.example.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@Controller
@RequestMapping("/api/posts")
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;
    private final Gson gson;

    public PostController(PostService service) {
        this.service = service;
        gson = new Gson();
    }

    @GetMapping
    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        response.getWriter().print(gson.toJson(data));
    }

    @GetMapping("/{id}")
    public void getById(@PathVariable long id, HttpServletResponse response) throws IOException {
        // TODO: deserialize request & serialize response
        response.setContentType(APPLICATION_JSON);
        final var post = service.getById(id);
        response.getWriter().print(gson.toJson(post));
    }

    @PostMapping
    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);
        response.getWriter().print(gson.toJson(data));
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id, HttpServletResponse response) {
        // TODO: deserialize request & serialize response
        response.setContentType(APPLICATION_JSON);
        service.removeById(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}