package vn.khanguyen.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.khanguyen.backend.domain.User;
import vn.khanguyen.backend.service.UserService;
import vn.khanguyen.backend.util.error.UserNullException;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createUser = this.userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser);

    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws UserNullException {
        User updatedUser = this.userService.updateUser(user);
        if (updatedUser == null) {
            throw new UserNullException("User with id " + user.getId() + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNullException {
        if (this.userService.deleteUser(id) == null) {
            throw new UserNullException("User with id " + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
