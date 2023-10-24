package com.thzy.social.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @className: DefaultRetry
 * @description:
 * @author: TT-Berg
 * @date: 2023/7/25
 **/
public final class DefaultRetryService implements RetryStandardService {

    /**
     * 默认重试次数
     */
    public static final int DEFAULT_MAX_ATTEMPTS = 3;

    /**
     * 默认间隔时间
     */
    private static final long DEFAULT_BACK_OFF_PERIOD = 1L;

    /**
     * 重试次数
     */
    private final int maxAttempts;

    /**
     * 重试时间间隔 (秒)
     */
    private final long sleepSeconds;

    public DefaultRetryService() {
        this(DEFAULT_MAX_ATTEMPTS, DEFAULT_BACK_OFF_PERIOD);
    }

    public DefaultRetryService(int maxAttempts) {
        this(maxAttempts, DEFAULT_BACK_OFF_PERIOD);
    }

    public DefaultRetryService(int maxAttempts, long sleepMillis) {
        this.maxAttempts = maxAttempts;
        this.sleepSeconds = (sleepMillis > 0 ? sleepMillis : 1);
    }

    private Logger log = LoggerFactory.getLogger(DefaultRetryService.class);

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public long getSleepSeconds() {
        return sleepSeconds;
    }

    @Override
    public <T, E extends Throwable> T execute(RetryCallback<T, E> retryCallback) throws E {
        int retryCount;
        Throwable lastThrowable = null;

        for (int i = 0; i < maxAttempts; i++) {
            try {
                return retryCallback.call();
            } catch (Throwable e) {
                retryCount = i + 1;
                log.warn("retry on {} times error{}.", retryCount, e.getMessage());
                lastThrowable = e;
                if (sleepSeconds > 0 && retryCount < maxAttempts) {
                    try {
                        Thread.sleep(sleepSeconds * 1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        if (lastThrowable == null) {
            lastThrowable = new RuntimeException("retry on " + maxAttempts + " times,still fail.");
        }

        throw new RuntimeException(lastThrowable);
    }

    @Override
    public <T, E extends Throwable> T executeAndRetryBySpecifyException(RetryCallback<T, E> retryCallback,
                                                                        Class<? extends Throwable> throwable) throws E {
        int retryCount;
        Throwable lastThrowable = null;

        for (int i = 0; i < maxAttempts; i++) {
            try {
                return retryCallback.call();
            } catch (Throwable e) {
                if (!throwable.isAssignableFrom(e.getClass())) {
                    throw new RuntimeException(e);
                }
                retryCount = i + 1;
                log.warn("retry on {} times error{}.", retryCount, e.getMessage());
                lastThrowable = e;
                if (sleepSeconds > 0 && retryCount < maxAttempts) {
                    try {
                        Thread.sleep(sleepSeconds * 1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        if (lastThrowable == null) {
            lastThrowable = new RuntimeException("retry on " + maxAttempts + " times,still fail.");
        }

        throw new RuntimeException(lastThrowable);
    }
}