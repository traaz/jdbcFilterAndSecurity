package com.example.jdbcFilterQuery.Controller;

import com.example.jdbcFilterQuery.Models.User;
import com.example.jdbcFilterQuery.Repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getUserFromFilter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false)  String surname,
            @RequestParam(required = false)  Integer age
    ){
        return userRepository.getUserFromFilter(name, surname, age);
    }

    @GetMapping("/getall")
    public List<User> getAllUsers(){
        return userRepository.getAllUser();
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id){
        return userRepository.getUserById(id);
    }
    @PutMapping("/update/{id}")
    public String updateUser(@RequestBody User user, @PathVariable int id){
        return userRepository.updateUser(id, user);
    }



}