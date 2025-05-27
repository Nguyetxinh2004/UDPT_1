package space.example.commonservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;

import space.example.commonservice.common.Constant;
import space.example.commonservice.util.AuthenticationUtil;

public class OpenFeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = AuthenticationUtil.getToken();

        if (token != null) {
            requestTemplate.header(HttpHeaders.AUTHORIZATION, Constant.HeaderConstant.BEARER_PREFIX + token);
        }

    }
}
