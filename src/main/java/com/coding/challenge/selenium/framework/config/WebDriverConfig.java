package com.coding.challenge.selenium.framework.config;

import com.coding.challenge.selenium.framework.annotation.BrowserScopeBean;
import com.coding.challenge.selenium.framework.annotation.LazyConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;

@LazyConfiguration
@Slf4j
@Profile({"local"})
public class WebDriverConfig {

    @BrowserScopeBean
    @ConditionalOnProperty(name = "browser", havingValue = "firefox")
    public WebDriver firefoxDriver() {
        return new FirefoxDriver();
    }

    @BrowserScopeBean
    @ConditionalOnProperty(name = "browser", havingValue = "edge")
    public WebDriver edgeDriver() {
        return new EdgeDriver();
    }

    @BrowserScopeBean
    @ConditionalOnProperty(name = "browser", havingValue = "chrome")
    public WebDriver chromeDriver() {
        log.info("Starting browser with Chrome");
        return new ChromeDriver();
    }

}
