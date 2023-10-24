package com.thzy.social.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @className: UrlBuilderTest
 * @description:
 * @author: TT-Berg
 * @date: 2023/7/25
 **/
public class UrlBuilderTest {

    @Test
    public void urlBuildTestForShortUrl() {
        String url = "http://www.qpp.com";
        String param1Key = "access_token";
        String param1Value = "aesratsadfasdasfasdagase";
        String param2Key = "id";
        String param2Value = "24152142";

        String build = UrlBuilder.fromBaseUrl(url)
                .queryParam(param1Key, param1Value)
                .queryParam(param2Key, param2Value)
                .build();

        Assert.assertNotNull(build);
        Assert.assertTrue(build.equals(url + "?" + param1Key + "=" + param1Value + "&" + param2Key + "=" + param2Value));
    }

    @Test
    public void urlBuildTestForLongUrl() {
        String url = "http://www.qpp.com?test=test";
        String param1Key = "access_token";
        String param1Value = "aesratsadfasdasfasdagase";
        String param2Key = "id";
        String param2Value = "24152142";

        String build = UrlBuilder.fromBaseUrl(url)
                .queryParam(param1Key, param1Value)
                .queryParam(param2Key, param2Value)
                .build();

        Assert.assertNotNull(build);
        Assert.assertTrue(build.equals(url + "&" + param1Key + "=" + param1Value + "&" + param2Key + "=" + param2Value));
    }
}
