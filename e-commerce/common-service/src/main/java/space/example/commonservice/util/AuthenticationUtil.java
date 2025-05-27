package space.example.commonservice.util;

import com.auth0.jwt.JWT;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import space.example.commonservice.common.Constant;

@Slf4j(topic = "AuthenticationUtil")
public class AuthenticationUtil {

    public static String getCurrentToken() {
        HttpServletRequest request = getCurrentHttpRequest();
        if (request == null) {
            return null;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith(Constant.HeaderConstant.BEARER_PREFIX)) {
            return authHeader.substring(Constant.HeaderConstant.BEARER_PREFIX.length());
        }
        return null;
    }

    public static String extractUserId(String token) {
        return JWT.decode(token).getSubject();
    }


    public static Long getCurrentUserId() {
        Authentication authentication = getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            String token = jwtAuth.getToken().getTokenValue();

            log.info("token: {}", token);

            return Long.valueOf(extractUserId(token));
        }

        return null;
    }

    public static String getToken() {
        Authentication authentication = getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken().getTokenValue();
        }
        return null;
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private static HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletAttributes) {
            return servletAttributes.getRequest();
        }
        return null;
    }
}
