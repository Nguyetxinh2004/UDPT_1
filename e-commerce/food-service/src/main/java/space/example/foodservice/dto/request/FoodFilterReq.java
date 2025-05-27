package space.example.foodservice.dto.request;

import lombok.Data;

@Data
public class FoodFilterReq {
    private String name;
    private String category;
    private String tag;
    private Integer priceFrom;
    private Integer priceTo;
}
