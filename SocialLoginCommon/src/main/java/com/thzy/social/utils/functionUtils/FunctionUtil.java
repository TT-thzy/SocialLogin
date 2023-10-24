package com.thzy.social.utils.functionUtils;

import com.qpp.core.exception.BusinessException;
import com.thzy.social.utils.functionUtils.funtionInterface.BranchHandleFunction;
import com.thzy.social.utils.functionUtils.funtionInterface.ThrowBusinessExceptionFunction;
import com.thzy.social.utils.functionUtils.funtionInterface.ThrowExceptionFunction;
import com.qpp.web.core.exception.BusinessExceptionBuilder;

/**
 * @className: FunctionUtil
 * @description:
 * @author: TT-Berg
 * @date: 2022/10/11
 **/
public class FunctionUtil {

    /**
     * flag为true时，抛出异常
     *
     * @param flag
     * @return
     */
    public static ThrowBusinessExceptionFunction doThrowWithCondition(Boolean flag) {
        return (code, errorParam) -> {
            if (flag) {
                if (null == errorParam) {
                    throw BusinessExceptionBuilder.of(code);
                }
                throw BusinessExceptionBuilder.of(code, errorParam);
            }
        };
    }

    /**
     * 抛出异常
     *
     * @return
     */
    public static ThrowBusinessExceptionFunction doThrow() {
        return (code, errorParam) -> {
            if (null == errorParam) {
                throw BusinessExceptionBuilder.of(code);
            }
            throw BusinessExceptionBuilder.of(code, errorParam);
        };
    }

    /**
     * true执行trueHandle逻辑，false执行falseHandle逻辑
     *
     * @param flag
     * @return
     */
    public static BranchHandleFunction doBranchHandle(Boolean flag) {
        return (trueHandle, falseHandle) -> {
            if (flag) {
                trueHandle.run();
            } else {
                falseHandle.run();
            }
        };
    }

    /**
     * flag为true时，抛出异常
     *
     * @param flag
     * @return
     */
    public static ThrowExceptionFunction doThrowWithConditionSimple(Boolean flag) {
        return message -> {
            if (flag) {
                throw new BusinessException(message);
            }
        };
    }

    /**
     * 抛出异常
     *
     * @return
     */
    public static ThrowExceptionFunction doThrowSimple() {
        return message -> {
            throw new BusinessException(message);
        };
    }
}
