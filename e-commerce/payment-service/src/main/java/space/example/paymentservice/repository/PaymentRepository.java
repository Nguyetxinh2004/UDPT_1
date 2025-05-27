package space.example.paymentservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import space.example.paymentservice.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
