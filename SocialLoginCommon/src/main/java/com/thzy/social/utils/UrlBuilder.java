package com.thzy.social.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @className: UrlBuilder
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
public class UrlBuilder {

    private StringBuilder baseUrl = new StringBuilder();

    private UrlBuilder() {

    }

    private StringBuilder append(String baseUrl) {
        this.baseUrl.append(baseUrl);
        return this.baseUrl;
    }

    private String getBaseUrl() {
        return this.baseUrl.toString();
    }

    /**
     * @param baseUrl 基础路径
     * @return the new {@code UrlBuilder}
     */
    public static UrlBuilder fromBaseUrl(String baseUrl) {
        UrlBuilder builder = new UrlBuilder();
        builder.append(baseUrl);
        return builder;
    }

    /**
     * 添加参数
     *
     * @param key   参数名称
     * @param value 参数值
     * @return this UrlBuilder
     */
    public UrlBuilder queryParam(String key, Object value) {
        if (StringUtils.isBlank(key)) {
            throw new RuntimeException("参数名不能为空");
        }
        if (null == value) {
            throw new RuntimeException("参数值不能为空");
        }
        if (this.getBaseUrl().contains("?")) {
            this.append("&").append(key).append("=").append(value);
        } else {
            this.append("?").append(key).append("=").append(value);
        }
        return this;
    }

    /**
     * 构造url
     *
     * @return url
     */
    public String build() {
        return this.getBaseUrl();
    }

}
