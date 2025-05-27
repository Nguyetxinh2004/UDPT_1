package space.example.authservice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import space.example.authservice.entity.User;
import space.example.authservice.util.JwtUtil;
import space.example.commonservice.common.Constant;
import space.example.commonservice.exception.MissingTokenException;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/refresh-token",
            "/api/auth/verify-email",
            "/api/auth/forgot-password",
            "/api/auth/reset-password",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/api-docs/**"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isSecuredPath(request)) {
            String token = extractTokenFromHeader(request);

            jwtUtil.validateToken(token);
            authenticateUser(token);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String token) {
        String username = jwtUtil.extractAccessToken(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = (User) userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );

            authenticationToken.setDetails(user);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith(Constant.HeaderConstant.BEARER_PREFIX)) {
            throw new MissingTokenException(Constant.ErrorCode.AUTH_MISSING_TOKEN);
        }
        return authorizationHeader.substring(Constant.HeaderConstant.BEARER_PREFIX.length());
    }


    private boolean isSecuredPath(HttpServletRequest request) {
        AntPathMatcher matcher = new AntPathMatcher();
        String requestURI = request.getRequestURI();

        boolean isSecured = EXCLUDED_PATHS.stream().noneMatch(pattern -> matcher.match(pattern, requestURI));

        if (isSecured) {
            log.info("🔐 Secured path: {}", requestURI);
        } else {
            log.info("🔓 Unsecured path (excluded): {}", requestURI);
        }

        return isSecured;
    }

}
