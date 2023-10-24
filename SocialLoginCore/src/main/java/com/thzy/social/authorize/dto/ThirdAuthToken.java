package com.thzy.social.authorize.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: ThirdAuthToken
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
@Data
@Builder
public class ThirdAuthToken implements Serializable {

    private String accessToken;

    private long expireIn;

    private String scope;

    private String tokenType;

    private String idToken;
}
