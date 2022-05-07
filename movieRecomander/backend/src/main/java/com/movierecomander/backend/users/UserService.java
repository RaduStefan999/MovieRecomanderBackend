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

    static Specification<User> userEmailContains(String email) {
        return (user, cq, cb) -> cb.like(user.get("email"), "%" + email + "%");
    }

    public List<User> getAppUserByEmail(String email) {
        return userRepository.findAll(userEmailContains(email));
    }
}
