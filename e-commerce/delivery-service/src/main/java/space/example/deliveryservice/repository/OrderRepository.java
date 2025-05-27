package space.example.deliveryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.example.deliveryservice.entity.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdAndUserId(Long id, Long userId);
}