package com.thzy.social.authorize.manager;

import com.thzy.social.domain.CommonProperty;
import com.thzy.socialLogin.idGenerate.IdGenerator;
import com.thzy.socialLogin.mongo.driver.HybridMongoTemplate;
import com.thzy.socialLogin.mongo.manager.AbstractLongMongoCurdManager;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

/**
 * @description:
 * @author: TT-Berg
 * @date: 2023/10/24
 **/
@Repository
public class CommonPropertyManager extends AbstractLongMongoCurdManager<CommonProperty, Long> {

    public CommonPropertyManager(HybridMongoTemplate mongoTemplate, IdGenerator idgenerator) {
        super(mongoTemplate, idgenerator);
    }

    public @Nullable String getValue(String key) {
        CommonProperty commonProperty = mongoTemplate.findOne(Query.query(Criteria.where("key").is(key)), CommonProperty.class);
        if (null == commonProperty) {
            return null;
        }
        return commonProperty.getValue();
    }
}
