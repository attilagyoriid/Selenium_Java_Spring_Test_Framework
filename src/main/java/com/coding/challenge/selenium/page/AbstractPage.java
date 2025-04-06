package com.coding.challenge.selenium.page;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public abstract class AbstractPage {


    @Autowired
    protected WebDriver driver;

    @Autowired
    protected WebDriverWait wait;

    @PostConstruct
    private void init() {
        PageFactory.initElements(this.driver, this);
    }

    protected abstract boolean isAt();

    protected void maximizeWindow() {
        driver.manage().window().maximize();
    }

    protected void goTo(String url) {
        this.driver.get(url);
    }

    protected void handleConsentDialog(WebElement consentButton) {
        try {
            WebElement actualConsentButton = wait.until(ExpectedConditions.elementToBeClickable(consentButton));
            actualConsentButton.click();
            wait.until(ExpectedConditions.invisibilityOf(actualConsentButton));
        } catch (TimeoutException e) {
            log.info("Consent dialog did not appear within timeout");
        }
    }

    protected String waitForInputValueToSettle(WebElement inputElement) {

        AtomicReference<String> lastValue = new AtomicReference<>("");

        return wait.until(driver -> {
            String currentValue = inputElement.getAttribute("value");
            if (currentValue.equals(lastValue.get())) {
                return currentValue;
            }

            lastValue.set(currentValue);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return null;
        });
    }

    protected void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")
                .equals("complete"));
    }


}
