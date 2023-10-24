package com.thzy.social.authorize.manager;

import com.thzy.social.authorize.domain.ThirdAuthRecord;
import com.thzy.social.enumerate.ThirdAuthSource;
import com.thzy.socialLogin.idGenerate.IdGenerator;
import com.thzy.socialLogin.mongo.driver.HybridMongoTemplate;
import com.thzy.socialLogin.mongo.manager.AbstractLongMongoCurdManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @className: ThirdAuthRecordManager
 * @description:
 * @author: TT-Berg
 * @date: 2023/7/26
 **/
@Repository
public class ThirdAuthRecordManager extends AbstractLongMongoCurdManager<ThirdAuthRecord, Long> {

    public ThirdAuthRecordManager(HybridMongoTemplate mongoTemplate, IdGenerator idgenerator) {
        super(mongoTemplate, idgenerator);
    }

    public ThirdAuthRecord findRecordByUserIdAndSource(Long userId, ThirdAuthSource source) {
        return mongoTemplate.findOne(Query.query(Criteria.where("userId").is(userId)
                .and("source").is(source)), ThirdAuthRecord.class);
    }
}
