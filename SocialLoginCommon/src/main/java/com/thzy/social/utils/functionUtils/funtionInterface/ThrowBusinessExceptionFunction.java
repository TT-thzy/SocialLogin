package com.thzy.social.utils.functionUtils.funtionInterface;

import java.util.Map;

/**
 * @className: ThrowBusinessExceptionFunction
 * @description:
 * @author: TT-Berg
 * @date: 2022/10/11
 **/
@FunctionalInterface
public interface ThrowBusinessExceptionFunction {

    /**
     *
     * @param code  错误码
     * @param errorParam    错误参数
     */
    void throwException(int code, Map<String, Object> errorParam);
}
