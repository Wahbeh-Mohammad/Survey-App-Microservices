package com.atypon.authentication.controllers;

import com.atypon.authentication.models.User;
import com.atypon.authentication.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    UserRepository repository;

    public AuthenticationController(UserRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User requestUser) {
        if(requestUser == null) return new ResponseEntity<String>("Invalid credentials", HttpStatus.BAD_REQUEST);

        User responseUser = repository.fetchUser(requestUser);

        if(responseUser == null)
            return new ResponseEntity<String>("Invalid user credentials/Username not registered", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<User>(responseUser, HttpStatus.ACCEPTED);
    }

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User requestUser) {
        if(requestUser == null) return new ResponseEntity<String>("Invalid credentials", HttpStatus.BAD_REQUEST);

        User responseUser = repository.createUser(requestUser);

        if(responseUser == null)
            return new ResponseEntity<String>("Invalid user credentials/Missing values", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<User>(responseUser, HttpStatus.ACCEPTED);
    }
}
