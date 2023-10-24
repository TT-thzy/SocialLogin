package com.thzy.social.utils;

import com.thzy.socialLogin.exception.BusinessException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @className: DigestUtils
 * @description:
 * @author: TT-Berg
 * @date: 2023/7/25
 **/
public class DigestUtils {

    /**
     * md5
     *
     * @param str
     * @return
     */
    public final static String MD5Encode(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException("摘要算法异常： " + e);
        }
        messageDigest.update(str.getBytes());
        return Base64.getEncoder().encodeToString(messageDigest.digest());
    }
}
