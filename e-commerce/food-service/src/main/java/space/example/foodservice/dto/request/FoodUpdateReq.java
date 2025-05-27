package space.example.foodservice.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class FoodUpdateReq {
    private String name;
    private String description;
    private double price;
    private String category;
    private boolean available;
    private List<String> tags;
}