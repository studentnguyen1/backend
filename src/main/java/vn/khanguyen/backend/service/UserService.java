package vn.khanguyen.backend.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.khanguyen.backend.domain.User;
import vn.khanguyen.backend.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User userCur) {
        User user = userRepository.findById(userCur.getId()).orElse(null);
        if (user != null) {
            user.setName(userCur.getName());
            user.setEmail(userCur.getEmail());
            return userRepository.save(user);
        }
        return null;
    }
}
