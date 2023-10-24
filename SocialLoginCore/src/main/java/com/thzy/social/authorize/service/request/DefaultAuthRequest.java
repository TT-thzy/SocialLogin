package com.thzy.social.authorize.service.request;

import com.google.common.collect.ImmutableMap;
import com.thzy.social.authorize.domain.ThirdAuthConfig;
import com.thzy.social.authorize.dto.ThirdAuthCallback;
import com.thzy.social.authorize.dto.ThirdAuthToken;
import com.thzy.social.authorize.dto.ThirdAuthUserInfo;
import com.thzy.social.authorize.service.cache.ThirdAuthStateCache;
import com.thzy.social.constant.RedisTagConstant;
import com.thzy.social.enumerate.ThirdAuthSource;
import com.thzy.social.retry.DefaultRetryService;
import com.thzy.social.retry.RetryStandardService;
import com.thzy.social.utils.HttpUtils;
import com.thzy.social.utils.UUIDUtils;
import com.thzy.social.utils.UrlBuilder;
import com.thzy.social.utils.functionUtils.FunctionUtil;
import com.thzy.social.utils.RestTemplateBuilder;
import com.thzy.socialLogin.exception.BusinessExceptionBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: DefaultAuthRequest
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
public abstract class DefaultAuthRequest implements ThirdAuthRequest {

    protected ThirdAuthConfig authConfig;

    protected ThirdAuthStateCache stateCache;

    public DefaultAuthRequest(ThirdAuthConfig authConfig, ThirdAuthStateCache stateCache) {
        this.authConfig = authConfig;
        this.stateCache = stateCache;

        //校验配置
//        this.checkConfig(authConfig);
    }

    @Override
    public ThirdAuthUserInfo authorizeByClientServer(String accessToken) {
        String authorizeUrl = UrlBuilder
                .fromBaseUrl(authConfig.getAuthorizeUrl())
                .queryParam("access_token", accessToken)
                .build();

        RestTemplateBuilder.Proxy proxy = this.getProxy();
        RestTemplate restTemplate = RestTemplateBuilder.buildRestTemplate(authConfig.getConnectTimeout(), authConfig.getReadTimeout(), proxy);
        RetryStandardService retryService = new DefaultRetryService(authConfig.getRetryTimes(), authConfig.getSleepSeconds());

        Map<String, Object> result = null;
        try {
            result = retryService.<Map<String, Object>, Exception>executeAndRetryBySpecifyException(
                    () -> restTemplate.getForObject(authorizeUrl, HashMap.class), RestClientException.class
            );
        } catch (Exception e) {
            throw BusinessExceptionBuilder.of(5220011, ImmutableMap.of("accessToken", accessToken,
                    "message", e.getMessage()));
        }

        this.checkResponse(result, accessToken);

        return convertUserInfo(result);
    }

    @Override
    public String getAuthorizeUrl(String state, Map<String, String> args) {
        return this.generateAuthorizeUrl(this.handleState(state), args);
    }

    @Override
    public ThirdAuthUserInfo authorize(ThirdAuthCallback callback) {
        FunctionUtil.doThrowWithConditionSimple(StringUtils.isBlank(callback.getCode()))
                .throwException("callback data of code can not be null");

        this.checkState(callback.getState());

        this.handelProxy();

        ThirdAuthToken token = this.getAccessToken(callback);

        return this.getUserInfo(token);
    }

    private void checkState(String state) {
        if (!authConfig.isIgnoreCheckState()) {
            FunctionUtil.doThrowWithConditionSimple(StringUtils.isBlank(state)
                            || !stateCache.contains(RedisTagConstant.thirdAuthState + state))
                    .throwException("callback data of state is illegal");
            if (ThirdAuthStateCache.STATE_USE_ONCE.equals(authConfig.getStateExpiredStrategy())) {
                stateCache.remove(RedisTagConstant.thirdAuthState + state);
            }
        }
    }

    protected abstract ThirdAuthToken getAccessToken(ThirdAuthCallback callback);

    protected abstract ThirdAuthUserInfo getUserInfo(ThirdAuthToken token);

    protected abstract String generateAuthorizeUrl(String state, Map<String, String> args);

    protected abstract ThirdAuthUserInfo convertUserInfo(Map<String, Object> response);

    protected abstract void checkResponse(Map<String, Object> response, String accessToken);

    protected String handleState(String state) {
        if (StringUtils.isBlank(state)) {
            state = UUIDUtils.getUUID();
        }
        stateCache.cache(RedisTagConstant.thirdAuthState, state, state);
        return state;
    }

    private void checkConfig(ThirdAuthConfig config) {
        FunctionUtil.doThrowWithConditionSimple(StringUtils.isBlank(config.getClientId()))
                .throwException(String.format("ThirdAuthRequest: the config %s clientId can not be null", config.getId()));

        FunctionUtil.doThrowWithConditionSimple(StringUtils.isBlank(config.getClientSecret()))
                .throwException(String.format("ThirdAuthRequest: the config %s clientSecret can not be null", config.getId()));

        FunctionUtil.doThrowWithConditionSimple(StringUtils.isBlank(config.getRedirectUrl()))
                .throwException(String.format("ThirdAuthRequest: the config %s redirectUrl can not be null", config.getId()));

        FunctionUtil.doThrowWithConditionSimple(CollectionUtils.isEmpty(config.getScopes()))
                .throwException(String.format("ThirdAuthRequest: the config %s scopes can not be null", config.getId()));

        FunctionUtil.doThrowWithConditionSimple(StringUtils.isBlank(config.getAuthorizeUrl()))
                .throwException(String.format("ThirdAuthRequest: the config %s authorizeUrl can not be null", config.getId()));

        if (config.getSource().equals(ThirdAuthSource.facebook)) {
            FunctionUtil.doThrowWithConditionSimple(!HttpUtils.isHttpsProtocol(config.getRedirectUrl()))
                    .throwException(String.format("ThirdAuthRequest: the facebook auth config %s redirectUrl must be https protocol", config.getId()));
        }
    }

    protected String concatParam(String redirectUrl, Map<String, String> args) {
        if (null == args || args.size() < 1) {
            return redirectUrl;
        }
        for (String key : args.keySet()) {
            String value = args.get(key);
            redirectUrl = UrlBuilder.fromBaseUrl(redirectUrl)
                    .queryParam(key, value)
                    .build();
        }
        return redirectUrl;
    }

    private void handelProxy() {
        if (authConfig.isNeedProxy()) {
            FunctionUtil.doThrowWithConditionSimple(null == authConfig.getProxy())
                    .throwException(String.format("ThirdAuthRequest: the auth config %s proxy can not be null", authConfig.getId()));

            FunctionUtil.doThrowWithConditionSimple(StringUtils.isBlank(authConfig.getProxy().getIp()))
                    .throwException(String.format("ThirdAuthRequest: the auth config %s proxy's ip can not be null", authConfig.getId()));

            FunctionUtil.doThrowWithConditionSimple(null == authConfig.getProxy().getPort())
                    .throwException(String.format("ThirdAuthRequest: the auth config %s proxy's port can not be null", authConfig.getId()));

            if (ThirdAuthSource.google.equals(authConfig.getSource())) {
                if (System.getProperty("https.proxyPort") == null) {
                    System.setProperty("https.proxyPort", authConfig.getProxy().getPort().toString());
                }
                if (System.getProperty("https.proxyHost") == null) {
                    System.setProperty("https.proxyHost", authConfig.getProxy().getIp());
                }
                if (System.getProperty("com.google.api.client.should_use_proxy") == null) {
                    System.setProperty("com.google.api.client.should_use_proxy", "true");
                }
            }
        }
    }


    private RestTemplateBuilder.Proxy getProxy() {
        if (authConfig.isNeedProxy()) {
            return new RestTemplateBuilder.Proxy(authConfig.getProxy().getIp(), authConfig.getProxy().getPort());
        }
        return null;
    }
}
