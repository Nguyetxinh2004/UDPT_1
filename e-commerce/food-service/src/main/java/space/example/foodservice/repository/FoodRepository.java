package space.example.foodservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import space.example.foodservice.entity.Food;

public interface FoodRepository extends MongoRepository<Food, String> {
    boolean existsById(String string);
}
