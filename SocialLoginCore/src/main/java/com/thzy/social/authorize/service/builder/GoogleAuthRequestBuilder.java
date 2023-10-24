package com.thzy.social.authorize.service.builder;

import com.thzy.social.authorize.domain.GoogleAuthConfig;
import com.thzy.social.authorize.domain.ThirdAuthConfig;
import com.thzy.social.authorize.service.cache.ThirdAuthStateCache;
import com.thzy.social.authorize.service.request.GoogleAuthRequest;
import org.springframework.stereotype.Component;

/**
 * @className: GoogleAuthRequestBuilder
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
@Component
public class GoogleAuthRequestBuilder implements ThirdAuthRequestBuilder<GoogleAuthConfig, GoogleAuthRequest> {

    @Override
    public GoogleAuthRequest build(GoogleAuthConfig config, ThirdAuthStateCache thirdAuthStateCache) {
        return new GoogleAuthRequest(config, thirdAuthStateCache);
    }

    @Override
    public boolean support(ThirdAuthConfig config) {
        return config instanceof GoogleAuthConfig;
    }

}
