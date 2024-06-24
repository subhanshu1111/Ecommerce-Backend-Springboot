package com.example.first_draft.controller;



import com.example.first_draft.entity.User;
import com.example.first_draft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

@Autowired
    private UserService userService;
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/getUserById/{id}")
    public Optional<User> getUserById(@PathVariable("id") Long id){
        return userService.findByUserId(id);
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user){
        return userService.save(user);
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable("id") Long id){
         userService.delete(id);
    }

}
