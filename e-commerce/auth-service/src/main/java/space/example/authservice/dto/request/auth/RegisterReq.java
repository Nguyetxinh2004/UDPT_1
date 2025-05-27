package space.example.authservice.dto.request.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterReq {
    private String username;

    private String email;

    private String password;

    private String fullName;

    private String phoneNumber;

    private String role;
}
