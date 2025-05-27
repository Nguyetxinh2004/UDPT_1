package space.example.foodservice.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "foods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {
    @Id
    private String id;

    private String name;
    private String description;
    private double price;
    private String category;
    private boolean available;
    private List<String> tags;
}
