package space.example.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.example.authservice.dto.request.auth.LoginReq;
import space.example.authservice.dto.request.auth.RegisterReq;
import space.example.authservice.dto.response.auth.LoginResp;
import space.example.authservice.dto.response.auth.RegisterResp;
import space.example.authservice.service.AuthService;
import space.example.commonservice.dto.response.ApiResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @GetMapping("/test")
    public String auth() {
        return "Auth Service";
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResp>> register(
            @RequestBody RegisterReq registerReq
    ) {
        RegisterResp registerResp = authService.register(registerReq);
        ApiResponse<RegisterResp> response = ApiResponse.<RegisterResp>builder()
                .data(registerResp)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResp>> login(
            @RequestBody LoginReq loginReq
    ) {
        LoginResp loginResp = authService.login(loginReq);
        ApiResponse<LoginResp> response = ApiResponse.<LoginResp>builder()
                .data(loginResp)
                .build();
        return ResponseEntity.ok(response);
    }
}
