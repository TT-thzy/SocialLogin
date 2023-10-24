package com.thzy.social.domain;

import com.thzy.socialLogin.mongo.domain.LongMongoDomain;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "commonproperties")
@Data
public class CommonProperty extends LongMongoDomain {

    private String key;

    private String value;

}
