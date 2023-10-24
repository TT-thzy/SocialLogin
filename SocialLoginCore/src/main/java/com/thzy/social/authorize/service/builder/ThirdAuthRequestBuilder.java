package com.thzy.social.authorize.service.builder;

import com.thzy.social.authorize.domain.ThirdAuthConfig;
import com.thzy.social.authorize.service.cache.ThirdAuthStateCache;
import com.thzy.social.authorize.service.request.ThirdAuthRequest;

/**
 * @className: ThirdAuthRequestBuilder
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
public interface ThirdAuthRequestBuilder<T extends ThirdAuthConfig, R extends ThirdAuthRequest> {

    R build(T config, ThirdAuthStateCache stateCache);

    boolean support(ThirdAuthConfig config);
}
