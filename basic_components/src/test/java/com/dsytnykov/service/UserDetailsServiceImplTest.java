package com.dsytnykov.service;

import com.dsytnykov.model.User;
import com.dsytnykov.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl service;

    @Test
    void shouldNotFindUserByUsername() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("username"));
    }

    @Test
    void shouldFindUserByUsername() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(User.builder().id(1L).username("username").password("password").roles(new HashSet<>(List.of("ROLE_USER"))).build()));

        UserDetails user = service.loadUserByUsername("username");

        assertEquals("username", user.getUsername());
        assertEquals("ROLE_USER", user.getAuthorities().iterator().next().getAuthority());
    }

}