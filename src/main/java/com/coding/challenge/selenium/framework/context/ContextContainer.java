package com.coding.challenge.selenium.framework.context;

import java.util.HashMap;
import java.util.Map;

/**
 * ContextContainer is a generic class that holds a map of context keys and their corresponding values.
 * It provides a way to store and retrieve context-specific data between cucumber steps
 *
 * @param <T> the type of the values stored in the context
 */
public class ContextContainer<T> {

    private Map<ContextKeys, T> map = new HashMap<>();

    public Map<ContextKeys, T> getMap() {
        return map;
    }
}
