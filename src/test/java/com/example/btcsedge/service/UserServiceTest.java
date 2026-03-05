package com.example.btcsedge.service;

import com.example.btcsedge.domain.enums.UserRole;
import com.example.btcsedge.domain.model.User;
import com.example.btcsedge.dto.CreateUserDto;
import com.example.btcsedge.dto.UserDto;
import com.example.btcsedge.repo.RoleRepository;
import com.example.btcsedge.repo.UserRepository;
import com.example.btcsedge.service.impl.UserServiceImpl;
import com.example.btcsedge.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User adminUser;
    private User regularUser;

    @BeforeEach
    void setUp() {
        adminUser = TestDataBuilder.buildAdminUser();
        regularUser = TestDataBuilder.buildRegularUser();
    }

    @Test
    void authenticate_withCorrectCredentials_returnsUser() {
        when(userRepository.findByEmail("admin@btcs.com")).thenReturn(Optional.of(adminUser));
        when(passwordEncoder.matches("admin123", adminUser.getPassword())).thenReturn(true);

        Optional<User> result = userService.authenticate("admin@btcs.com", "admin123");

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("admin@btcs.com");
    }

    @Test
    void authenticate_withWrongPassword_returnsEmpty() {
        when(userRepository.findByEmail("admin@btcs.com")).thenReturn(Optional.of(adminUser));
        when(passwordEncoder.matches("wrongpass", adminUser.getPassword())).thenReturn(false);

        Optional<User> result = userService.authenticate("admin@btcs.com", "wrongpass");

        assertThat(result).isEmpty();
    }

    @Test
    void authenticate_withNonExistentUser_returnsEmpty() {
        when(userRepository.findByEmail("nobody@btcs.com")).thenReturn(Optional.empty());

        Optional<User> result = userService.authenticate("nobody@btcs.com", "anypass");

        assertThat(result).isEmpty();
    }

    @Test
    void create_encodesPasswordAndSavesUser() {
        CreateUserDto dto = new CreateUserDto();
        dto.setUsername("newuser");
        dto.setEmail("new@btcs.com");
        dto.setPassword("plaintext");

        User savedUser = TestDataBuilder.buildUser(10L, "new@btcs.com", "newuser", "encoded", UserRole.USER);
        when(passwordEncoder.encode("plaintext")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDto result = userService.create(dto);

        assertThat(result.getEmail()).isEqualTo("new@btcs.com");
        verify(passwordEncoder).encode("plaintext");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void getUserByEmail_withExistingEmail_returnsUser() {
        when(userRepository.findByEmail("admin@btcs.com")).thenReturn(Optional.of(adminUser));

        Optional<User> result = userService.getUserByEmail("admin@btcs.com");

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("admin@btcs.com");
    }

    @Test
    void getUserByEmail_withNonExistentEmail_returnsEmpty() {
        when(userRepository.findByEmail("nobody@btcs.com")).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserByEmail("nobody@btcs.com");

        assertThat(result).isEmpty();
    }

    @Test
    void getPage_returnsPaginatedResults() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(List.of(adminUser, regularUser), pageable, 2);
        when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<UserDto> result = userService.getPage(pageable);

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).hasSize(2);
    }

    @Test
    void updateUserRole_withExistingUser_updatesRole() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(adminUser));
        when(userRepository.save(any(User.class))).thenReturn(adminUser);

        UserDto result = userService.updateUserRole(1L, UserRole.OPERATOR);

        verify(userRepository).save(adminUser);
    }

    @Test
    void updateUserRole_withNonExistentUser_throwsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUserRole(99L, UserRole.USER))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void findByUsername_withExistingUsername_returnsUser() {
        when(userRepository.findByUsername("admin@btcs.com")).thenReturn(Optional.of(adminUser));

        User result = userService.findByUsername("admin@btcs.com");

        assertThat(result.getUsername()).isEqualTo("admin@btcs.com");
    }

    @Test
    void findByUsername_withNonExistentUsername_throwsException() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findByUsername("nonexistent"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
