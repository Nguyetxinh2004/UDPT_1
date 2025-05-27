package space.example.authservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.example.authservice.dto.request.auth.LoginReq;
import space.example.authservice.dto.request.auth.RegisterReq;
import space.example.authservice.dto.response.auth.LoginResp;
import space.example.authservice.dto.response.auth.RegisterResp;
import space.example.authservice.repository.AuthRepository;
import space.example.authservice.entity.User;
import space.example.authservice.util.JwtUtil;
import space.example.commonservice.common.Constant;
import space.example.commonservice.exception.BadCredentialsException;
import space.example.commonservice.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;


    @Transactional
    public RegisterResp register(RegisterReq req) {
        boolean isExists = authRepository.existsByUsername(req.getUsername());

        if (isExists) {
            throw new BadCredentialsException(Constant.ErrorCode.USER_ALREADY_EXISTS, req.getUsername());
        }

        User newUser = modelMapper.map(req, User.class);
        newUser.setPasswordHash(passwordEncoder.encode(req.getPassword()));

        return RegisterResp.of(authRepository.save(newUser));
    }

    @Transactional
    public LoginResp login(LoginReq loginReq) {
        User user = authRepository.findByUsername(loginReq.getUsername())
                .orElseThrow(() -> new NotFoundException(Constant.ErrorCode.USER_NOT_FOUND));

        if (user == null || !passwordEncoder.matches(loginReq.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(Constant.ErrorCode.USER_NOT_FOUND);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginReq.getUsername(),
                loginReq.getPassword(),
                user.getAuthorities()
        );

        authenticationManager.authenticate(authenticationToken);

        return LoginResp.builder()
                .accessToken(jwtUtil.generateAccessToken(user))
                .role(user.getRole())
                .username(user.getUsername())
                .build();
    }
}
