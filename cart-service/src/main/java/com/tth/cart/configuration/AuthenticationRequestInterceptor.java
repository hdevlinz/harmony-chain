package com.tth.cart.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class AuthenticationRequestInterceptor implements RequestInterceptor {

    private static final String TOKEN_HEADER = "Authorization";

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes httpRequest = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (httpRequest != null) {
            String authHeader = httpRequest.getRequest().getHeader(TOKEN_HEADER);

            if (StringUtils.hasText(authHeader)) {
                template.header(TOKEN_HEADER, authHeader);
            }
        }
    }

}
