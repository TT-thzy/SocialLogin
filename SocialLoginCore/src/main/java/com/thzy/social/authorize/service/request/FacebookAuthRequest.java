package com.thzy.social.authorize.service.request;

import com.thzy.social.authorize.domain.ThirdAuthConfig;
import com.thzy.social.authorize.dto.ThirdAuthCallback;
import com.thzy.social.authorize.dto.ThirdAuthToken;
import com.thzy.social.authorize.dto.ThirdAuthUserInfo;
import com.thzy.social.authorize.service.cache.ThirdAuthStateCache;

import java.util.Map;

/**
 * @className: FacebookAuthRequest
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
public class FacebookAuthRequest extends DefaultAuthRequest{

    public FacebookAuthRequest(ThirdAuthConfig authConfig, ThirdAuthStateCache stateCache) {
        super(authConfig, stateCache);
    }

    @Override
    protected ThirdAuthToken getAccessToken(ThirdAuthCallback callback) {
        return null;
    }

    @Override
    protected ThirdAuthUserInfo getUserInfo(ThirdAuthToken token) {
        return null;
    }

    @Override
    protected String generateAuthorizeUrl(String state, Map<String, String> args) {
        return null;
    }

    @Override
    protected ThirdAuthUserInfo convertUserInfo(Map<String, Object> response) {
        return null;
    }

    @Override
    protected void checkResponse(Map<String, Object> response, String accessToken) {

    }
}
