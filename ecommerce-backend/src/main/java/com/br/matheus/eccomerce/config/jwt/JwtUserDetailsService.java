package com.br.matheus.eccomerce.config.jwt;

import com.br.matheus.eccomerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        com.br.matheus.eccomerce.models.User user = userRepository.findByEmail(username).orElseThrow(null);
        return User.builder()
                .username(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .roles(user.getRoles().split(","))
                .build();
    }
}
