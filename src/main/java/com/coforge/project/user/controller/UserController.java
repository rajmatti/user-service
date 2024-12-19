package com.coforge.project.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.coforge.project.user.model.User;
import com.coforge.project.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	
    private final UserService userService;
   // private final AuthenticationManager authenticationManager;

    public UserController(UserService userService){//, AuthenticationManager authenticationManager) {
        this.userService = userService;
      //  this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/updateWallet")
    public ResponseEntity<String> updateWallet(@RequestBody User user) {
        User updatedUser = userService.updateWallet(user);
        return ResponseEntity.ok("Wallet updated successfully!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
       String isAuthenticated = userService.login(user.getUsername(),user.getPassword());
        if (isAuthenticated.equals("User logged in successfully!")) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }
    
//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody User user) {
//        try {
//            String response = userService.loginUser(user.getUsername(), user.getPassword());
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
    
    
//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody User user) {
//        try {
//            String response = userService.loginUser(user.getUsername(), user.getPassword());
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
}