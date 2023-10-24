package com.thzy.social.authorize.service.request;

import com.thzy.social.authorize.dto.ThirdAuthCallback;
import com.thzy.social.authorize.dto.ThirdAuthUserInfo;

import java.util.Map;

/**
 * @className: ThirdAuthRequest
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
public interface ThirdAuthRequest {

    /**
     * 获取授权地址
     *
     * @param state 防伪字段
     * @param args  回调地址请求参数
     * @return
     */
    String getAuthorizeUrl(String state, Map<String, String> args);

    /**
     * 授权操作
     *
     * @param callback 回调信息
     * @return
     */
    ThirdAuthUserInfo authorize(ThirdAuthCallback callback);

    /**
     * 通过access_token直接过去用户信息
     *
     * @param accessToken
     * @return
     */
    ThirdAuthUserInfo authorizeByClientServer(String accessToken);
}
