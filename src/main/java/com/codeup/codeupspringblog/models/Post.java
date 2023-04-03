package com.codeup.codeupspringblog.models;

import com.codeup.codeupspringblog.models.User;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 1024, nullable = false, unique = false)
    private String title;

    @Column(length = 1024, nullable = true, unique = false)
    private String body;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", user=" + user +
                '}';
    }
}
