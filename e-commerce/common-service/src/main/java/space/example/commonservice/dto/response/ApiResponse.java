package space.example.commonservice.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class ApiResponse<T> {
    private T data;
    private String message;
}