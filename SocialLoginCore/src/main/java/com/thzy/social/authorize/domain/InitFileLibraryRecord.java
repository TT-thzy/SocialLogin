package com.thzy.social.authorize.domain;

import com.thzy.socialLogin.mongo.domain.LongMongoDomain;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @className: InitFileLibraryRecord
 * @description:
 * @author: TT-Berg
 * @date: 2023/7/24
 **/
@Document("init_library_records")
@Data
public class InitFileLibraryRecord extends LongMongoDomain {

    private Long userId;

    private boolean successInit;

    private String errorMessage;

    private String errorStrace;

}
