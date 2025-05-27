package space.example.deliveryservice.dto.response.food;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDto {
    private String id;
    private String name;
    private String description;
    private Integer price;
    private String category;
    private boolean available;
    private List<String> tags;
}
