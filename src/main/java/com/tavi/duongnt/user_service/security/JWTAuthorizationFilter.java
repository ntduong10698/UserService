package com.tavi.duongnt.user_service.security;

import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.repository.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository appUserRepo;

    private JWTService jwtService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository appUserRepo, JWTService jwtService) {
        super(authenticationManager);
        this.appUserRepo = appUserRepo;
        this.jwtService = jwtService;
    }

    // Xác nhận dua user vao he thong
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        if (header != null && header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            //   System.out.println("no authorization");
        }
        chain.doFilter(request, response);
    }

    //  read token và cấp quyền
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token != null) {
            String username = jwtService.decode(token);
            if (username != null) {
                UserEntity appUser = appUserRepo.findByUsername(username);
                System.out.println("User Principal: " + appUser.getUsername());
                return new UsernamePasswordAuthenticationToken(username, null, new HashSet<>());
            }
        }
        return null;
    }


}
