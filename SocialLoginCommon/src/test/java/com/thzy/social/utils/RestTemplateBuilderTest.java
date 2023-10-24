package com.thzy.social.utils;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

/**
 * @className: RestTemplateBuilderTest
 * @description:
 * @author: TT-Berg
 * @date: 2023/7/25
 **/
public class RestTemplateBuilderTest {

    @Test
    public void buildRestTemplate() {
        int connectTime = 20000;
        int readTimeout = 30000;
        RestTemplateBuilder.Proxy proxy = new RestTemplateBuilder.Proxy("192.168.41.188", 80);

        RestTemplate restTemplate = RestTemplateBuilder.buildRestTemplate(connectTime, readTimeout, proxy);

        assertNotNull(restTemplate);
        assertNotNull(restTemplate.getRequestFactory());
    }
}