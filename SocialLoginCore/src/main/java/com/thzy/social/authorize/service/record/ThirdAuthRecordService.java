package com.thzy.social.authorize.service.record;

import com.thzy.social.authorize.domain.ThirdAuthRecord;
import com.thzy.social.authorize.manager.ThirdAuthRecordManager;
import com.thzy.social.enumerate.ThirdAuthSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: ThirdAuthRecordService
 * @description:
 * @author: TT-Berg
 * @date: 2023/7/26
 **/
@Service
public class ThirdAuthRecordService {

    @Autowired
    private ThirdAuthRecordManager thirdAuthRecordManager;

    public void createRecord(String thirdUserId, Long userId, ThirdAuthSource source, String email) {
        ThirdAuthRecord record = thirdAuthRecordManager.findRecordByUserIdAndSource(userId, source);
        if (null != record) {
            return;
        }
        ThirdAuthRecord newRecord = new ThirdAuthRecord();
        newRecord.setUserId(userId);
        newRecord.setThirdUserId(thirdUserId);
        newRecord.setSource(source);
        newRecord.setEmail(email);

        thirdAuthRecordManager.saveNew(newRecord);
    }

}
