package com.thzy.social.authorize.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @className: ThirdAuthCallback
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
@Data
public class ThirdAuthCallback implements Serializable {

    private String code;

    private String state;

    private String prompt;

    private String scope;

    private String authuser;

    private Map<String, String> args;
}
