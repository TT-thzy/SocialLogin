package com.thzy.social.utils;

import java.util.UUID;

/**
 * @className: UuIDUtils
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
public class UUIDUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

}
