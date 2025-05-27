package space.example.authservice.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.example.authservice.dto.request.auth.RegisterReq;
import space.example.authservice.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResp {
    private Long id;
    private String username;

    public static RegisterResp of(User user) {
        return RegisterResp.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
