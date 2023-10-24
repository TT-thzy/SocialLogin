package com.thzy.social.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @className: HttpHeaderBuilder
 * @description:
 * @author: TT-Berg
 * @date: 2023/8/7
 **/
public class HttpHeaderBuilder {

    public static HttpHeaders build(Map<String, Object> headerParam) {
        HttpHeaders headers = new HttpHeaders();
        if (!CollectionUtils.isEmpty(headerParam)) {
            headerParam.forEach((key, value) -> {
                headers.add(key, (String) value);
            });
        }
        return headers;
    }
}
