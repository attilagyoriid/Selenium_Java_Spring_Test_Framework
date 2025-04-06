package com.coding.challenge.selenium.page.calkooapp;

import com.coding.challenge.selenium.framework.annotation.LazyAutowired;
import com.coding.challenge.selenium.framework.annotation.Page;
import com.coding.challenge.selenium.page.AbstractPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.stream.Collectors;

//ToDo it could be decomposed into views and multiple page objects
@Page
@Slf4j
public class ValueAddedTaxCalculatorPage extends AbstractPage {

    @LazyAutowired
    WebDriverWait webDriverWait;

    @FindBy(css = "#vatcalculator select[name='Country']")
    private WebElement inputSelectCountry;

    @FindBy(css = "label.css-label[for^='VAT_']")
    private List<WebElement> labelVatRates;

    @FindBy(css = "#F1")
    private WebElement inputRadioPriceWoVAT;

    @FindBy(css = "#F2")
    private WebElement inputRadioValueAddedTax;

    @FindBy(css = "#F3")
    private WebElement inputRadioPriceInclVAT;

    @FindBy(css = "#NetPrice")
    private WebElement inputTextPriceWoVAT;

    @FindBy(css = "#VATsum")
    private WebElement inputTextValueAddedTax;

    @FindBy(css = "#Price")
    private WebElement inputTextPriceInclVAT;

    @FindBy(css = "#VATpct2")
    private WebElement inputTextPriceWoVATExplanation;

    @FindBy(css = "#VATpct1")
    private WebElement inputPriceInclVATExplanation;

    @FindBy(css = "input[value='Reset']")
    private WebElement buttonReset;

    @FindBy(css = "#chart_div")
    private WebElement pieChart;
    @FindBy(css = "#chart_div svg g:nth-of-type(1)")
    private WebElement pieChartVAT;

    @FindBy(css = "#chart_div svg g:nth-of-type(2)")
    private WebElement pieChartPrice;

    @FindBy(css = "#cl-consent a[data-role='b_agree']")
    private WebElement constentDialogAgreeButton;
    @FindBy(css = "button[aria-label='Consent']")
    private WebElement welcomeToCalkooDialogConstentButton;

    public void selectCountryByVisibleText(String countryName) {
        log.info("Selecting country: {}", countryName);
        Select select = new Select(inputSelectCountry);
        select.selectByVisibleText(countryName);
    }

    @Override
    public void goTo(String url) {
        log.info("Navigating to URL: {}", url);
        super.goTo(url);
        handleConsentDialog(constentDialogAgreeButton);
        handleConsentDialog(welcomeToCalkooDialogConstentButton);
    }

    public void clickVatRateLabelByText(String vatText) {
        log.info("Clicking VAT rate label with text: {}", vatText);
        labelVatRates.stream()
                .filter(label -> label.getText().trim().equals(vatText))
                .findFirst()
                .ifPresentOrElse(
                        WebElement::click,
                        () -> {
                            throw new NoSuchElementException("VAT label with text '" + vatText + "' not found");
                        }
                );
    }

    public List<String> getVatRateLabels() {
        log.info("Getting all VAT rate labels");
        return labelVatRates.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public void selectCalculationInputTypeRadioByName(String inputType) {
        log.info("Selecting calculation input type radio button: {}", inputType);
        WebElement radioButton = getRadioButtonElement(inputType);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", radioButton);
    }

    public String getInputFieldValueByName(String inputType) {
        log.info("Getting input field value of type: {}", inputType);
        WebElement inputElement = getInputElement(inputType);
        return inputElement.getDomProperty("value").trim();
    }

    public String getInputFieldExplanationByName(String inputType) {
        log.info("Getting input field explanation of type: {}", inputType);
        WebElement inputElement = getExplanationElement(inputType);
        return inputElement.getAttribute("value").trim();
    }

    public Set<String> getInputFieldClassesByName(String inputType) {
        log.info("Getting input field classes of type: {}", inputType);
        WebElement inputElement = getInputElement(inputType);
        String classAttribute = inputElement.getAttribute("class");
        return classAttribute != null ?
                new HashSet<>(Arrays.asList(classAttribute.split("\\s+"))) :
                new HashSet<>();
    }

    public void setInputFieldValueByName(String inputType, String value) {
        log.info("Setting input field value of type: {} to {}", inputType, value);
        WebElement inputElement = getInputElement(inputType);
        inputElement.clear();
        inputElement.sendKeys(value);
    }


    public String getValueOfInputPriceInclVATExplanation() {
        log.info("Getting value of inputPriceInclVATExplanation");
        return inputPriceInclVATExplanation.getAttribute("value");
    }

    public void pressResetButton() {
        log.info("Pressing reset button");
        buttonReset.click();
        waitForPageLoad();
    }

    public void clickResetAndWaitForPageLoad() {
        log.info("Clicking reset button and waiting for page load");
        try {
            buttonReset.click();
            wait.until(ExpectedConditions.elementToBeClickable(inputSelectCountry));
        } catch (TimeoutException e) {
            throw new TimeoutException("Reset operation did not complete within 10 seconds", e);
        }
    }


    public String getPieChartVATText() {
        log.info("Getting pie chart VAT text");
        webDriverWait.until(ExpectedConditions.visibilityOf(pieChart));
        return pieChartVAT.findElement(By.cssSelector("text")).getText().trim();
    }

    public String getPieChartPriceText() {
        log.info("Getting pie chart price text");
        webDriverWait.until(ExpectedConditions.visibilityOf(pieChart));
        return pieChartPrice.findElement(By.cssSelector("text")).getText().trim();
    }

    public String getPieChartVATFillColor() {
        log.info("Getting pie chart VAT fill color");
        webDriverWait.until(ExpectedConditions.visibilityOf(pieChart));
        return pieChartVAT.findElement(By.cssSelector("path:nth-of-type(1)")).getAttribute("fill");
    }

    public String getPieChartPriceFillColor() {
        log.info("Getting pie chart price fill color");
        webDriverWait.until(ExpectedConditions.visibilityOf(pieChart));
        return pieChartPrice.findElement(By.cssSelector("path:nth-of-type(1)")).getAttribute("fill");
    }

    public String getSelectedCountry() {
        log.info("Getting selected country");

        Select select = new Select(inputSelectCountry);
        WebElement selectedOption = select.getFirstSelectedOption();
        return selectedOption.getText().trim();
    }

    @Override
    public boolean isAt() {
        return false;
    }

    private WebElement getRadioButtonElement(String inputType) {
        log.info("Getting radio button element of type: {}", inputType);
        return switch (inputType) {
            case "Price without VAT" -> inputRadioPriceWoVAT;
            case "Value-Added Tax" -> inputRadioValueAddedTax;
            case "Price incl. VAT" -> inputRadioPriceInclVAT;
            default -> throw new IllegalArgumentException("Invalid input type: " + inputType);
        };
    }

    private WebElement getInputElement(String inputType) {
        log.info("Getting input element of type: {}", inputType);
        return switch (inputType) {
            case "Price without VAT" -> inputTextPriceWoVAT;
            case "Value-Added Tax" -> inputTextValueAddedTax;
            case "Price incl. VAT" -> inputTextPriceInclVAT;
            default -> throw new IllegalArgumentException("Invalid input type: " + inputType);
        };
    }

    private WebElement getExplanationElement(String inputType) {
        log.info("Getting explanation element of type: {}", inputType);
        return switch (inputType) {
            case "Price without VAT" -> inputTextPriceWoVATExplanation;
            case "Price incl. VAT" -> inputPriceInclVATExplanation;
            default -> throw new IllegalArgumentException("Invalid input type: " + inputType);
        };
    }


}
