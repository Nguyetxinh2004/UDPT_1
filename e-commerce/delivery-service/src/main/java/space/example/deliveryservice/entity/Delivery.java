package space.example.deliveryservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "deliveries", schema = "delivery_service_db")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "delivery_time")
    private LocalDateTime deliveryTime;

    @Size(max = 100)
    @Column(name = "shipper_name", length = 100)
    private String shipperName;

    @Size(max = 20)
    @Column(name = "shipper_phone", length = 20)
    private String shipperPhone;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

}