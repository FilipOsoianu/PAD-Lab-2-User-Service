package lab_2.user_service.controller;

import lab_2.user_service.entities.User;
import lab_2.user_service.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    final
    UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(UserController.class);

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    ResponseEntity<List<User>> getUsers() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(userList, HttpStatus.OK);

    }

    @GetMapping("/users/{id}")
    ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    ResponseEntity<User> createUser(@RequestBody User newUser) {
        User user = userRepository.save(newUser);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    ResponseEntity<User> updateUser(@RequestBody User updatedUser, @PathVariable Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());
            user.setBirthDate(updatedUser.getBirthDate());
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
