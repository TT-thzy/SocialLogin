package com.thzy.social.authorize.service.builder;

import com.thzy.social.authorize.domain.FacebookAuthConfig;
import com.thzy.social.authorize.domain.ThirdAuthConfig;
import com.thzy.social.authorize.service.cache.ThirdAuthStateCache;
import com.thzy.social.authorize.service.request.FacebookAuthRequest;
import org.springframework.stereotype.Component;

/**
 * @className: FacebookAuthRequestBuilder
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
@Component
public class FacebookAuthRequestBuilder implements ThirdAuthRequestBuilder<FacebookAuthConfig, FacebookAuthRequest> {

    @Override
    public FacebookAuthRequest build(FacebookAuthConfig config, ThirdAuthStateCache stateCache) {
        return new FacebookAuthRequest(config, stateCache);
    }

    @Override
    public boolean support(ThirdAuthConfig config) {
        return config instanceof FacebookAuthConfig;
    }

}
