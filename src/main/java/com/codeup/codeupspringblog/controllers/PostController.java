package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.Post;
import com.codeup.codeupspringblog.repositories.PostRepository;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/posts")
public class PostController {

    private final PostRepository postDao;

    @GetMapping
    public String all(Model model) {
        List<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
        return "posts/index";
    }

    @GetMapping(path = "/{postId}")
    public String individualPost(Model model, @PathVariable long postId) {
        Post post = postDao.findById(postId).get();
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping(path = "/create")
    public String viewCreatePostForm(Model model) {
        model.addAttribute("newPost", new Post());
        return "posts/create";
    }

    @PostMapping(path = "/create")
    public String creatingPost(@ModelAttribute Post newPost) {
        postDao.save(newPost);
        return "redirect:/posts";
    }

}
