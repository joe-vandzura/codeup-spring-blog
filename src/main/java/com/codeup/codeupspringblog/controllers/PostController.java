package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import com.codeup.codeupspringblog.services.EmailService;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final EmailService emailService;

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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newPost.setUser(user);
        postDao.save(newPost);
        emailService.prepareAndSend(newPost, "New Post", "Your new post has been created.");
        return "redirect:/posts";
    }

    @GetMapping(path = "/{postId}/edit")
    public String viewCEditPostPage(Model model, @PathVariable Long postId) {
        Post clickedPost = postDao.findById(postId).get();
        model.addAttribute("post", clickedPost);
        return "posts/edit";
    }

    @PostMapping(path = "/{postId}/edit")
    public String editingPost(@ModelAttribute Post newPost, @PathVariable Long postId) {
        Post editedPost = postDao.findById(postId).get();
        editedPost.setTitle(newPost.getTitle());
        editedPost.setBody(newPost.getBody());
        postDao.save(editedPost);
        return "redirect:/posts";
    }

}
