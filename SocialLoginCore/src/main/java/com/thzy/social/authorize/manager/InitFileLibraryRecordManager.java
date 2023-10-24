package com.thzy.social.authorize.manager;

import com.thzy.social.authorize.domain.InitFileLibraryRecord;
import com.thzy.socialLogin.idGenerate.IdGenerator;
import com.thzy.socialLogin.mongo.driver.HybridMongoTemplate;
import com.thzy.socialLogin.mongo.manager.AbstractLongMongoCurdManager;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @className: InitFileLibraryRecordManager
 * @description:
 * @author: TT-Berg
 * @date: 2023/7/24
 **/
@Repository
public class InitFileLibraryRecordManager extends AbstractLongMongoCurdManager<InitFileLibraryRecord, Long> {

    public InitFileLibraryRecordManager(HybridMongoTemplate mongoTemplate, IdGenerator idgenerator) {
        super(mongoTemplate, idgenerator);
    }

    public InitFileLibraryRecord findRecordByUserId(Long userId) {
        return mongoTemplate.findOne(Query.query(Criteria.where("userId").is(userId)), InitFileLibraryRecord.class);
    }

}
