package com.coding.challenge.selenium.framework.context;

import com.coding.challenge.selenium.framework.annotation.LazyConfiguration;
import com.coding.challenge.selenium.framework.annotation.ThreadScopeBean;
import io.restassured.response.Response;

//ToDo create more comprehensive context

/**
 * This class is used to store context information for Cucumber tests.
 * It uses ThreadScopeBean to ensure that each thread has its own instance of the context.
 * The context is used to store information that needs to be shared between steps in a scenario.
 */
@LazyConfiguration
public class CucumberContext {

    @ThreadScopeBean
    public ContextContainer<Response> getContextContainerResponse() {
        return new ContextContainer<>();
    }

    @ThreadScopeBean
    public ContextContainer<String> getContextContainerString() {
        return new ContextContainer<>();
    }

    @ThreadScopeBean
    public ContextContainer<Integer> getContextContainerInt() {
        return new ContextContainer<>();
    }
}
