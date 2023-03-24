package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/posts")
public class PostController {

    @GetMapping
    public String indexPage(Model model) {
        List<Post> posts = new ArrayList<>();
        Post postOne = new Post("Hello", "This is my first post!");
        Post postTwo = new Post("Second post", "And this is my second post!");
        posts.add(postOne);
        posts.add(postTwo);
        model.addAttribute("posts", posts);
        return "posts/index";
    }

    @GetMapping(path = "/{id}")
    public String individualPost(Model model, @PathVariable int id) {
        Post post = new Post("Hello", "This is my first post!");
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping(path = "/create")
    @ResponseBody
    public String viewCreatePostForm() {
        return "view the form for creating a post";
    }

    @PostMapping(path = "/create")
    @ResponseBody
    public String creatingPost() {
        return "create a new post";
    }

}
