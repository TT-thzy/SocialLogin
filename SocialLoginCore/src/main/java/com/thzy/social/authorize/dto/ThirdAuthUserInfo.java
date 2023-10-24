package com.thzy.social.authorize.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @className: ThirdAuthUserInfo
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
@Data
@Builder
public class ThirdAuthUserInfo {

    private String thirdUserId;

    private String username;

    private String nickname;

    private String email;

    private String avatar;

}
