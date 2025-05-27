package space.example.deliveryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.example.deliveryservice.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    OrderItem findByOrderIdAndFoodId(Long orderId, String foodId);
}