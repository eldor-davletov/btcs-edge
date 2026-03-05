package com.example.btcsedge.config;

import com.example.btcsedge.DataInitializer;
import com.example.btcsedge.domain.enums.UserRole;
import com.example.btcsedge.domain.model.User;
import com.example.btcsedge.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataInitializerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DataInitializer dataInitializer;

    @Test
    void run_createsThreeUsers_whenNoneExist() throws Exception {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedpassword");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        dataInitializer.run();

        verify(userRepository, times(3)).save(any(User.class));
    }

    @Test
    void run_skipsExistingUsers() throws Exception {
        when(userRepository.existsByEmail("admin@btcs.com")).thenReturn(true);
        when(userRepository.existsByEmail("user@btcs.com")).thenReturn(true);
        when(userRepository.existsByEmail("operator@btcs.com")).thenReturn(true);

        dataInitializer.run();

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void run_assignsCorrectRoles() throws Exception {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        dataInitializer.run();

        List<User> savedUsers = captor.getAllValues();
        assertThat(savedUsers).hasSize(3);

        assertThat(savedUsers.stream()
                .anyMatch(u -> u.getUserRole() == UserRole.ADMIN)).isTrue();
        assertThat(savedUsers.stream()
                .anyMatch(u -> u.getUserRole() == UserRole.USER)).isTrue();
        assertThat(savedUsers.stream()
                .anyMatch(u -> u.getUserRole() == UserRole.OPERATOR)).isTrue();
    }

    @Test
    void run_encryptsPasswords() throws Exception {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        dataInitializer.run();

        verify(passwordEncoder, times(3)).encode(anyString());
    }

    @Test
    void run_setsActiveFlag() throws Exception {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        dataInitializer.run();

        List<User> savedUsers = captor.getAllValues();
        assertThat(savedUsers).allMatch(User::isActive);
    }
}
