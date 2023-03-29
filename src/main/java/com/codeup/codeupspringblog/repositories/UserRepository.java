package com.codeup.codeupspringblog.repositories;

import com.codeup.codeupspringblog.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
