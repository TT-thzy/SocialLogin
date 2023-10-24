package com.thzy.social.retry;

/**
 * @className: RetryStandardService
 * @description: 简单重试service（重试使用spring-retry || guava-retry）
 * @author: TT-Berg
 * @date: 2023/7/25
 **/
public interface RetryStandardService {

    /**
     * 异常重试执行方法
     *
     * @param <T>           the return value
     * @param retryCallback
     * @param <E>           the exception thrown
     * @return T the return value
     * @throws E the exception thrown
     */
    <T, E extends Throwable> T execute(RetryCallback<T, E> retryCallback) throws E;

    /**
     * 异常尝试执行方法(当指定异常(包括其子类)抛出时，才重试)
     *
     * @param <T>           the return value
     * @param retryCallback
     * @param throwable     the specify exception
     * @param <E>           the exception thrown
     * @return T the return value
     * @throws E the exception thrown
     */
    <T, E extends Throwable> T executeAndRetryBySpecifyException(RetryCallback<T, E> retryCallback, Class<? extends Throwable> throwable) throws E;
}
