package com.thzy.social.utils;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @className: HttpUtils
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
public class HttpUtils {

    private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

    /**
     * 是否为http协议
     *
     * @param url 待验证的url
     * @return true: http协议, false: 非http协议
     */
    public static boolean isHttpProtocol(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        return url.startsWith("http://") || url.startsWith("http%3A%2F%2F");
    }

    /**
     * 是否为https协议
     *
     * @param url 待验证的url
     * @return true: https协议, false: 非https协议
     */
    public static boolean isHttpsProtocol(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        return url.startsWith("https://") || url.startsWith("https%3A%2F%2F");
    }
}
