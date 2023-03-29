package com.codeup.codeupspringblog;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "blog_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 1024, nullable = false, unique = true)
    private String username;

    @Column(length = 1024, nullable = false, unique = false)
    private String email;

    @Column(length = 1024, nullable = false, unique = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")

    private List<Post> posts;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", posts=" + posts +
                '}';
    }
}