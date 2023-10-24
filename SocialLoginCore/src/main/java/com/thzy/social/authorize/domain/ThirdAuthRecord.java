package com.thzy.social.authorize.domain;

import com.thzy.social.enumerate.ThirdAuthSource;
import com.thzy.socialLogin.mongo.domain.LongMongoDomain;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @className: ThirdAuthRecord
 * @description:
 * @author: TT-Berg
 * @date: 2023/7/26
 **/
@Data
@Document("third_auth_records")
public class ThirdAuthRecord extends LongMongoDomain {

    private String thirdUserId;

    private Long userId;

    private ThirdAuthSource source;

    private String email;
}
