package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {

    @GetMapping(path = "/posts")
    @ResponseBody
    public String indexPage() {
        return "posts index page";
    }

    @GetMapping(path = "/posts/{id}")
    @ResponseBody
    public String individualPost(@PathVariable int id) {
        return "view an individual post and here is the id parameter: " + id;
    }

    @GetMapping(path = "/posts/create")
    @ResponseBody
    public String viewCreatePostForm() {
        return "view the form for creating a post";
    }

    @PostMapping(path = "posts/create")
    @ResponseBody
    public String creatingPost() {
        return "create a new post";
    }

}
