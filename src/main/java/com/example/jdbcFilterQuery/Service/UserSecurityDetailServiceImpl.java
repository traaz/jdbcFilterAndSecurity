package com.example.jdbcFilterQuery.Service;

import com.example.jdbcFilterQuery.Models.UserDetail;
import com.example.jdbcFilterQuery.Models.UserSecurity;
import com.example.jdbcFilterQuery.Repository.UserSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserSecurityRepository userSecurityRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSecurity user = userSecurityRepository.findByName(username);
        if (user == null){
            throw new UsernameNotFoundException("No user found");
        }
        return new UserDetail(user);
    }
}
