package space.example.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.example.authservice.entity.User;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
