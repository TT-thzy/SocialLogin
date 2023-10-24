package com.thzy.social.authorize.service.builder;

import com.thzy.social.authorize.domain.ThirdAuthConfig;
import com.thzy.social.authorize.dto.ThirdAuthCallback;
import com.thzy.social.authorize.dto.ThirdAuthUserInfo;
import com.thzy.social.authorize.service.cache.ThirdAuthStateRedisCache;
import com.thzy.social.utils.UUIDUtils;
import com.thzy.socialLogin.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @className: ThirdAuthRequestBuilderService
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/30
 **/
@Component
public class ThirdAuthRequestHandle {

    @Autowired
    private List<ThirdAuthRequestBuilder> thirdAuthRequestBuilders;

    @Autowired
    private ThirdAuthStateRedisCache thirdAuthStateRedisCache;

    public ThirdAuthRequestBuilder getBuilder(ThirdAuthConfig thirdAuthConfig) {
        return thirdAuthRequestBuilders.stream()
                .filter(item -> item.support(thirdAuthConfig))
                .findFirst()
                .orElseThrow(() -> new BusinessException("没有找的合适的ThirdAuthRequest构造者"));
    }

    public String getAuthorizeUrl(ThirdAuthConfig thirdAuthConfig, Map<String, String> args) {
        return this.getBuilder(thirdAuthConfig)
                .build(thirdAuthConfig, thirdAuthStateRedisCache)
                .getAuthorizeUrl(UUIDUtils.getUUID(), args);
    }

    public ThirdAuthUserInfo authorize(ThirdAuthConfig thirdAuthConfig, ThirdAuthCallback callback) {
        return this.getBuilder(thirdAuthConfig)
                .build(thirdAuthConfig, thirdAuthStateRedisCache)
                .authorize(callback);
    }

    public ThirdAuthUserInfo authorizeByClientServer(ThirdAuthConfig thirdAuthConfig, String accessToken) {
        return this.getBuilder(thirdAuthConfig)
                .build(thirdAuthConfig, null)
                .authorizeByClientServer(accessToken);
    }
}
