package com.coding.challenge.selenium.framework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * Utility class for handling wait times in Selenium tests. It is not recommended.
 * It provides methods to pause the execution for a specified duration.
 */
@Slf4j
public class HarnessUtils {
    @Value("${default.wait:3000}")
    private static long wait;

    /**
     * Pauses the execution for a specified duration.
     *
     * @param millis the duration in milliseconds to wait
     */

    public static void waitFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("Sleep interrupted: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Pauses the execution for the default duration specified in the application properties.
     */
    public static void waitFor() {
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            log.error("Sleep interrupted: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

