package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.Post;
import com.codeup.codeupspringblog.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
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

    private final UserRepository userDao;

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
        User candywandy = userDao.findById(1L).get();
        newPost.setUser(candywandy);
        postDao.save(newPost);
        return "redirect:/posts";
    }

}
