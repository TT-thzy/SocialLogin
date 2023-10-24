package com.thzy.social.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @className: RestTemplateBuilder
 * @description:
 * @author: TT-Berg
 * @date: 2023/7/21
 **/
public class RestTemplateBuilder {

    public static RestTemplate buildRestTemplate(Proxy proxy) {
        return buildRestTemplate(-1, -1, proxy);
    }

    public static RestTemplate buildRestTemplate(int connectTimeout, int readTimeout, Proxy proxy) {
        HttpClient httpClient = buildHttpClient(proxy);

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        if (connectTimeout > 0) {
            requestFactory.setConnectTimeout(connectTimeout);
        }

        if (readTimeout > 0) {
            requestFactory.setReadTimeout(readTimeout);
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    /**
     * 构建支持 http 和 https 的 HttpClient
     *
     * @return HttpClient
     */
    private static HttpClient buildHttpClient(Proxy proxy) {
        // 构建 Registry<ConnectionSocketFactory>
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", new SSLConnectionSocketFactory(buildSSLContext(), NoopHostnameVerifier.INSTANCE))
                .register("http", new PlainConnectionSocketFactory())
                .build();

        // 构建 HttpClientConnectionManager
        HttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        HttpClientBuilder builder = HttpClients.custom();

        if (null != proxy) {
            builder.setProxy(new HttpHost(proxy.getAddress(), proxy.getPort()));
        }

        // 构建 HttpClient
        return builder.setConnectionManager(httpClientConnectionManager).build();
    }

    private static SSLContext buildSSLContext() {
        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        SSLContext sslContext = null;

        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            throw new RuntimeException(e);
        }

        return sslContext;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Proxy {
        private String address;

        private int port;
    }
}
