package com.example.btcsedge.repository;

import com.example.btcsedge.domain.enums.UserRole;
import com.example.btcsedge.domain.model.User;
import com.example.btcsedge.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User buildAndSaveUser(String email, String username) {
        User user = User.builder()
                .email(email)
                .username(username)
                .password("encoded-password")
                .firstName("Test")
                .lastName("User")
                .userRole(UserRole.USER)
                .active(true)
                .build();
        return userRepository.save(user);
    }

    @Test
    void findByEmail_withExistingEmail_returnsUser() {
        buildAndSaveUser("test@btcs.com", "test@btcs.com");

        Optional<User> result = userRepository.findByEmail("test@btcs.com");

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("test@btcs.com");
    }

    @Test
    void findByEmail_withNonExistentEmail_returnsEmpty() {
        Optional<User> result = userRepository.findByEmail("nobody@btcs.com");

        assertThat(result).isEmpty();
    }

    @Test
    void existsByEmail_whenEmailExists_returnsTrue() {
        buildAndSaveUser("existing@btcs.com", "existing@btcs.com");

        assertThat(userRepository.existsByEmail("existing@btcs.com")).isTrue();
    }

    @Test
    void existsByEmail_whenEmailDoesNotExist_returnsFalse() {
        assertThat(userRepository.existsByEmail("notfound@btcs.com")).isFalse();
    }

    @Test
    void saveAndRetrieve_persistsAllFields() {
        User saved = buildAndSaveUser("save@btcs.com", "save@btcs.com");

        Optional<User> retrieved = userRepository.findById(saved.getId());

        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getEmail()).isEqualTo("save@btcs.com");
        assertThat(retrieved.get().getPassword()).isEqualTo("encoded-password");
        assertThat(retrieved.get().getUserRole()).isEqualTo(UserRole.USER);
        assertThat(retrieved.get().isActive()).isTrue();
    }

    @Test
    void delete_removesUser() {
        User saved = buildAndSaveUser("delete@btcs.com", "delete@btcs.com");
        Long id = saved.getId();

        userRepository.deleteById(id);

        assertThat(userRepository.findById(id)).isEmpty();
    }

    @Test
    void findAll_withPagination_returnsPaginatedResults() {
        buildAndSaveUser("page1@btcs.com", "page1@btcs.com");
        buildAndSaveUser("page2@btcs.com", "page2@btcs.com");
        buildAndSaveUser("page3@btcs.com", "page3@btcs.com");

        Page<User> page = userRepository.findAll(PageRequest.of(0, 2));

        assertThat(page.getSize()).isEqualTo(2);
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(3);
    }

    @Test
    void findByUsername_withExistingUsername_returnsUser() {
        buildAndSaveUser("username@btcs.com", "uniqueuser123");

        Optional<User> result = userRepository.findByUsername("uniqueuser123");

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("uniqueuser123");
    }
}
