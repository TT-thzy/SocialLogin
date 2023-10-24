package com.thzy.social.retry;

/**
 * @className: RetryCallback
 * @description:
 * @author: TT-Berg
 * @date: 2023/7/25
 **/

import java.io.Serializable;

public interface RetryCallback<T, E extends Throwable> extends Serializable {

    /**
     * 重试操作
     *
     * @return T
     * @throws E
     */
    T call() throws E;

}
