package com.blueBus.bBook.controller;

import com.blueBus.bBook.model.User;
import com.blueBus.bBook.security.JwtUtil;
import com.blueBus.bBook.security.UserDetailServiceImpl;
import com.blueBus.bBook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/home")
    public String home(){
        return "Welcome to home page";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user){
        if(userService.findByEmail(user.getEmail()) != null){
            return ResponseEntity.ok("User already exist");
        }
        userService.saveNewUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> signIn(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));


            UserDetails userDetails = userDetailService.loadUserByUsername(user.getUserName());
            String token = jwtUtil.getToken(userDetails.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>("Incorrect UserName and password",HttpStatus.BAD_REQUEST);
        }
    }
}
