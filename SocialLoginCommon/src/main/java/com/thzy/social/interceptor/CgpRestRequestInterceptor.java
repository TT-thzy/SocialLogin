package com.thzy.social.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

public class CgpRestRequestInterceptor implements RequestInterceptor {

    @Value("${cgp.token}")
    private String token;

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", token);
    }

}
