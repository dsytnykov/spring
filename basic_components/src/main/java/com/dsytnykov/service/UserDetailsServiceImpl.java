package com.dsytnykov.service;

import com.dsytnykov.model.User;
import com.dsytnykov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        Optional<User> domainUser = repository.findByUsername(username);
        if (domainUser.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user with name '" + username + "'");
        }
        Set<GrantedAuthority> roles = domainUser.get().getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(domainUser.get().getUsername(),domainUser.get().getPassword(), roles);
    }
}
