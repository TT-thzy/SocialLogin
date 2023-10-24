package com.thzy.social.authorize.service.request;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.common.collect.ImmutableMap;
import com.thzy.social.authorize.domain.ThirdAuthConfig;
import com.thzy.social.authorize.dto.ThirdAuthCallback;
import com.thzy.social.authorize.dto.ThirdAuthToken;
import com.thzy.social.authorize.dto.ThirdAuthUserInfo;
import com.thzy.social.authorize.service.cache.ThirdAuthStateCache;
import com.thzy.social.exception.ThirdAuthException;
import com.thzy.socialLogin.exception.BusinessExceptionBuilder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Map;

/**
 * @className: GoogleAuthRequest
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
public class GoogleAuthRequest extends DefaultAuthRequest {

    public GoogleAuthRequest(ThirdAuthConfig authConfig, ThirdAuthStateCache stateCache) {
        super(authConfig, stateCache);
    }

    @Override
    protected ThirdAuthToken getAccessToken(ThirdAuthCallback callback) {
        GoogleTokenResponse response = null;
        try {
            response = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(), new GsonFactory(),
                    authConfig.getClientId(), authConfig.getClientSecret(),
                    callback.getCode(), this.concatParam(authConfig.getRedirectUrl(), callback.getArgs()))
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.convertToken(response);
    }

    @Override
    protected ThirdAuthUserInfo getUserInfo(ThirdAuthToken token) {
        GoogleIdTokenVerifier tokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Arrays.asList(authConfig.getClientId()))
                .build();
        GoogleIdToken idToken = null;
        GoogleIdToken.Payload payload = null;
        try {
            idToken = tokenVerifier.verify(token.getIdToken());
            payload = idToken.getPayload();
        } catch (TokenResponseException e) {
            ThirdAuthException thirdAuthException = new ThirdAuthException();
            if (e.getDetails() != null) {
                thirdAuthException.setErrorCode(e.getDetails().getError());
                thirdAuthException.setErrorMessage(e.getDetails().getErrorDescription());
            } else {
                thirdAuthException.setErrorCode(String.valueOf(e.getStatusCode()));
                thirdAuthException.setErrorMessage(e.getStatusMessage());
            }
            throw thirdAuthException;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ThirdAuthUserInfo.builder()
                .thirdUserId(payload.getUserId())
                .email(payload.getEmail())
                .build();
    }

    @Override
    protected String generateAuthorizeUrl(String state, Map<String, String> args) {
        return new GoogleAuthorizationCodeRequestUrl(authConfig.getClientId(),
                this.concatParam(authConfig.getRedirectUrl(), args), authConfig.getScopes())
                .setState(state).build();
    }

    private ThirdAuthToken convertToken(GoogleTokenResponse response) {
        return ThirdAuthToken.builder()
                .accessToken(response.getAccessToken())
                .idToken(response.getIdToken())
                .expireIn(response.getExpiresInSeconds())
                .scope(response.getScope())
                .build();
    }

    @Override
    protected ThirdAuthUserInfo convertUserInfo(Map<String, Object> response) {
        return ThirdAuthUserInfo.builder()
                .thirdUserId((String) response.get("id"))
                .email((String) response.get("email"))
                .username((String) response.get("name"))
                .build();
    }

    @Override
    protected void checkResponse(Map<String, Object> response, String accessToken) {
        if (response.containsKey("error") || response.containsKey("error_description")) {
            StringBuilder message = new StringBuilder();
            if (null != response.get("error")) {
                message.append(response.get("error"));
            }
            if (null != response.get("error_description")) {
                message.append(response.get("error_description"));
            }
            throw BusinessExceptionBuilder.of(5220011, ImmutableMap.of("accessToken", accessToken,
                    "message", message));
        }
    }
}
