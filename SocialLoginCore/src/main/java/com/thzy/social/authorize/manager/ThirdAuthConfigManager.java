package com.thzy.social.authorize.manager;

import com.thzy.social.authorize.domain.ThirdAuthConfig;
import com.thzy.social.enumerate.ThirdAuthSource;
import com.thzy.socialLogin.idGenerate.IdGenerator;
import com.thzy.socialLogin.mongo.driver.HybridMongoTemplate;
import com.thzy.socialLogin.mongo.manager.AbstractLongMongoCurdManager;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @className: ThirdAuthConfigManager
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
@Repository
public class ThirdAuthConfigManager extends AbstractLongMongoCurdManager<ThirdAuthConfig, Long> {

    public ThirdAuthConfigManager(HybridMongoTemplate mongoTemplate, IdGenerator idgenerator) {
        super(mongoTemplate, idgenerator);
    }

    public ThirdAuthConfig findConfigByThirdAuthSourceAndOperate(ThirdAuthSource source, ThirdAuthConfig.OperateType operateType) {
        return mongoTemplate.findOne(Query.query(Criteria.where("source").is(source)
                .and("operateType").is(operateType)), ThirdAuthConfig.class);
    }
}
