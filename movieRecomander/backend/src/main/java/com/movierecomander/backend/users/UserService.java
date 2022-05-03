package com.movierecomander.backend.users;

import com.movierecomander.backend.users.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAppUsers()
    {
        return userRepository.findAll();
    }

    static Specification<User> userNameContains(String name) {
        return (user, cq, cb) -> cb.like(user.get("name"), "%" + name + "%");
    }

    public List<User> getAppUser(String username) {
        return userRepository.findAll(userNameContains(username));
    }
}
