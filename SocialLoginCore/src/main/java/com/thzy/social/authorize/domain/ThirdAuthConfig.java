package com.thzy.social.authorize.domain;

import com.thzy.social.enumerate.ThirdAuthSource;
import com.thzy.socialLogin.mongo.domain.LongMongoDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @className: ThirdAuthConfig
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
@Document("third_auth_configs")
@Data
public abstract class ThirdAuthConfig extends LongMongoDomain {

    @ApiModelProperty("客户端id")
    private String clientId;

    @ApiModelProperty("授权成功后回调地址")
    private String redirectUrl;

    @ApiModelProperty("秘钥")
    private String clientSecret;

    @ApiModelProperty("获取token方式")
    private String responseType;

    @ApiModelProperty("申请访问的资源")
    private List<String> scopes;

    @ApiModelProperty("三方平台类型")
    private ThirdAuthSource source;

    @ApiModelProperty("是否忽略检查state值，默认不忽略")
    private boolean ignoreCheckState = false;

    @ApiModelProperty("访问是否需要代理, 默认不需要")
    private boolean isNeedProxy = false;

    @ApiModelProperty("操作类型")
    private OperateType operateType;

    @ApiModelProperty("代理")
    private Proxy proxy;

    /**
     * STATE_USE_IN_TIME：指定时间后删除
     * STATE_USE_ONCE：用一次就删掉
     */
    @ApiModelProperty("state过期策略")
    private String stateExpiredStrategy = "STATE_USE_ONCE";

    @ApiModelProperty("授权地址")
    private String authorizeUrl;

    @ApiModelProperty("连接等待时间")
    private int connectTimeout = -1;

    @ApiModelProperty("数据传输等待时间")
    private int readTimeout = -1;

    @ApiModelProperty("重试次数")
    private int retryTimes = 3;

    @ApiModelProperty("重试间隔时间")
    private int sleepSeconds = 2;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Proxy {

        private String ip;

        private Integer port;
    }

    public enum OperateType {
        login, register
    }
}
