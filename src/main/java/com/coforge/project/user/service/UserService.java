package com.coforge.project.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;



import com.coforge.project.user.model.User;
import com.coforge.project.user.repository.UserRepository;



@Service
public class UserService {
	
	/* private final UserRepository userRepository;
	    private final BCryptPasswordEncoder passwordEncoder;
	    private final AuthenticationManager authenticationManager;

	    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
	        this.userRepository = userRepository;
	        this.passwordEncoder = passwordEncoder;
	        this.authenticationManager = authenticationManager;
	    }*/
	@Autowired
	private  UserRepository userRepository;
	
	@Autowired
    private  BCryptPasswordEncoder bCryptPasswordEncoder;
  //  private final AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver;

//    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.userRepository = userRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//     //   this.authenticationManagerResolver = authenticationManagerResolver;
//    }

    
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return "User logged in successfully!";
        }
        return "Invalid username or password";
    }
    
   /* public String loginUser(String username, String password) {
        try {
            User user = userRepository.findByUsername(username);
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                AuthenticationManager authenticationManager = authenticationManagerResolver.resolve(null);
                Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return "User logged in successfully!";
            } else {
                throw new RuntimeException("Invalid username or password");
            }
        } catch (Exception e) {
            // Log the exception
            System.err.println("Error logging in: " + e.getMessage());
            throw new RuntimeException("Invalid username or password");
        }
    }*/

    public User registerUser(User user) {
        // Check if username or email already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists!");
        }

        // Set default wallet balance
        user.setWalletBalance(10000.0);

        // Encrypt the password
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // Save the user
        return userRepository.save(user);
    }

    public User updateWallet(User user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found!"));
        existingUser.setWalletBalance(user.getWalletBalance());
        return userRepository.save(existingUser);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
    }
    
    public Double getWalletBalance(Long userId) {
        return userRepository.findWalletBalanceByUserId(userId);
    }
    
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found!"));
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setWalletBalance(user.getWalletBalance());
        return userRepository.save(existingUser);
    }
    
   /* public String loginUser(String username, String password) {
        try {
            User user = userRepository.findByUsername(username);
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return "User logged in successfully!";
            } else {
                throw new RuntimeException("Invalid username or password");
            }
        } catch (Exception e) {
            // Log the exception
            System.err.println("Error logging in: " + e.getMessage());
            throw new RuntimeException("Invalid username or password");
        }
    
    }*/
}