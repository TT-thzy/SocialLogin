package com.thzy.social.retry;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * @className: DefaultRetryServiceTest
 * @description:
 * @author: TT-Berg
 * @date: 2023/7/25
 **/
public class DefaultRetryServiceTest {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    private static Integer increment() {
        return ATOMIC_INTEGER.incrementAndGet();
    }

    private static Integer incrementException() {
        int i = ATOMIC_INTEGER.incrementAndGet();
        int s = 0 / 0;
        return i;
    }

    @Test
    public void executeForNoException() {
        RetryStandardService retryStandardService = new DefaultRetryService(3, 2);
        Exception exception = null;
        try {
            retryStandardService.<Integer, Exception>execute(() -> DefaultRetryServiceTest.increment());
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNull(exception);
        assertTrue(1 == ATOMIC_INTEGER.get());
    }

    @Test
    public void executeForHasException() {
        RetryStandardService retryStandardService = new DefaultRetryService(3, 2);
        Exception exception = null;
        try {
            retryStandardService.<Integer, Exception>execute(() -> DefaultRetryServiceTest.incrementException());
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNotNull(exception);
        assertTrue(3 == ATOMIC_INTEGER.get());
    }

    @Test
    public void executeForHasExceptionCase2() {
        RetryStandardService retryStandardService = new DefaultRetryService(3, 2);
        Exception exception = null;
        try {
            retryStandardService.<Integer, Exception>executeAndRetryBySpecifyException(() -> DefaultRetryServiceTest.incrementException(), RuntimeException.class);
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNotNull(exception);
        assertTrue(3 == ATOMIC_INTEGER.get());
    }

    @Test
    public void executeForHasExceptionCase3() {
        RetryStandardService retryStandardService = new DefaultRetryService(3, 2);
        Exception exception = null;
        try {
            retryStandardService.<Integer, Exception>executeAndRetryBySpecifyException(() -> DefaultRetryServiceTest.incrementException(), IOException.class);
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNotNull(exception);
        assertTrue(1 == ATOMIC_INTEGER.get());
    }
}