package space.example.deliveryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.example.deliveryservice.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    boolean existsByOrderId(Long orderId);
}
