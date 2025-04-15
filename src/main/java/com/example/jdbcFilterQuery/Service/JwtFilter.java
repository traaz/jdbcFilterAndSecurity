package com.example.jdbcFilterQuery.Service;

import com.example.jdbcFilterQuery.Models.UserDetail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component

public class JwtFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private UserSecurityDetailServiceImpl userSecurityDetailService;

    public JwtFilter(JwtService jwtService, UserSecurityDetailServiceImpl userSecurityDetailService) {
        this.jwtService = jwtService;
        this.userSecurityDetailService = userSecurityDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


     String authHeader = request.getHeader("Authorization");
     if(authHeader == null || !authHeader.startsWith("Bearer ")){
         filterChain.doFilter(request, response);
         return;
     }
     String token = authHeader.substring(7);
     String username = jwtService.extractUserName(token);

     if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
         UserDetails userDetails = userSecurityDetailService.loadUserByUsername(username); //tokendan sadece username exp date falan ama burada password roller vb. gelir
         if(jwtService.validateToken(token)){
             UsernamePasswordAuthenticationToken authenticationToken =
                     new UsernamePasswordAuthenticationToken(userDetails, null, null);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
         }

     }
        filterChain.doFilter(request, response);

    }
}
