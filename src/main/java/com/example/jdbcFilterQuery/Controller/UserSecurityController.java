package com.example.jdbcFilterQuery.Controller;


import com.example.jdbcFilterQuery.Models.User;
import com.example.jdbcFilterQuery.Models.UserSecurity;
import com.example.jdbcFilterQuery.Repository.UserSecurityRepository;
import com.example.jdbcFilterQuery.Service.JwtService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
public class UserSecurityController {
    private UserSecurityRepository userSecurityRepository;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private RedisTemplate<String, String> redisTemplate;



    public UserSecurityController(UserSecurityRepository userSecurityRepository,
                                  PasswordEncoder encoder, AuthenticationManager authenticationManager,
                                  JwtService jwtService,
                                  RedisTemplate<String, String> redisTemplate) {
        this.userSecurityRepository = userSecurityRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/add")
    public String addUser(@RequestBody UserSecurity userAdd){
       UserSecurity checkUser = userSecurityRepository.findByName(userAdd.getName());
       if(checkUser != null){
           return "Kullanici Mevcut";
       }
        userAdd.setPassword(encoder.encode(userAdd.getPassword()));
       return userSecurityRepository.addUser(userAdd);

    }
    @PostMapping("/login")
    public String login(@RequestBody UserSecurity userLogin){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin.getName(), userLogin.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();  // Giriş yapan kullanıcı adı
            String token = jwtService.generateToken(userLogin.getName());
            //key name value token
            redisTemplate.opsForValue().set(authentication.getName(), token, 1, TimeUnit.MINUTES);
            return token;
        } catch (Exception e) {
            e.printStackTrace(); // Konsola tam exception çıkar

            return "Giriş Hatali ";
        }
    }


}
