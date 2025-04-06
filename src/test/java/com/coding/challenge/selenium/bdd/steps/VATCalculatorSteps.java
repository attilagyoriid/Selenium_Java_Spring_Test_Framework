package com.coding.challenge.selenium.bdd.steps;

import com.coding.challenge.selenium.framework.service.HarnessUtils;
import com.coding.challenge.selenium.framework.service.StringService;
import com.coding.challenge.selenium.page.calkooapp.ValueAddedTaxCalculatorPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;

import java.util.List;
import java.util.Set;

@CucumberContextConfiguration
@SpringBootTest
public class VATCalculatorSteps {
    @Autowired
    private ValueAddedTaxCalculatorPage vatCalculatorPage;

    @Autowired
    private StringService stringService;

    @Given("user navigates to {string}")
    public void userNavigatesTo(String url) {
        vatCalculatorPage.goTo(url);
    }

    @When("user selects a country: {string}")
    public void userSelectsCountry(String country) {
        vatCalculatorPage.selectCountryByVisibleText(country);
    }

    @When("user selects a VAT rate: {string}")
    public void userSelectsVATRate(String vatRate) {
        vatCalculatorPage.clickVatRateLabelByText(vatRate);
    }

    @When("user selects {string} as the input type")
    public void userSelectsInputType(String inputType) {
        vatCalculatorPage.selectCalculationInputTypeRadioByName(inputType);
    }

    @Then("input: {string} is {string}")
    public void verifyInputFieldValue(String fieldName, String expectedValue) {

        String actualValue = vatCalculatorPage.getInputFieldValueByName(fieldName);
        System.out.println("getInputFieldValueByName = " + actualValue);
        if ("empty".equals(expectedValue)) {

            Assert.assertTrue(actualValue.isEmpty(), "Field should be empty");
        } else if ("NaN".equals(expectedValue)) {
            HarnessUtils.waitFor(4000);
            actualValue = vatCalculatorPage.getInputFieldValueByName(fieldName);
            Assert.assertEquals(actualValue,"NaN");
        } else {
            Assert.assertEquals(actualValue,expectedValue);
        }
    }

    @Then("input filed explanation for {string} displaying: {string}")
    public void verifyInputFieldExplanation(String fieldName, String expectedExplanation) {
        String actualExplanation = vatCalculatorPage.getInputFieldExplanationByName(fieldName);
        String actualExplanationDigitsOnly = stringService.extractNumberFromString(actualExplanation);
        Assert.assertEquals(actualExplanationDigitsOnly.trim(), expectedExplanation.trim(), "Field explanation doesn't match");
    }

    @Then("input: {string} has class {string}")
    public void verifyInputFieldClass(String fieldName, String className) {
        Set<String> inputFieldClasses = vatCalculatorPage.getInputFieldClassesByName(fieldName);
        Assert.assertTrue(inputFieldClasses.contains(className), "Field class doesn't match");
    }

    @When("input: {string} is edited with value: {string}")
    public void editInputFieldValue(String fieldName, String value) {
        vatCalculatorPage.setInputFieldValueByName(fieldName, value);
    }

    @Then("pie chart VAT section value is {string}")
    public void verifyPieChartVATSectionValue(String expectedValue) {
        String actualValue = vatCalculatorPage.getPieChartVATText();
        Assert.assertEquals(
                actualValue, expectedValue, "VAT section value doesn't match");
    }

    @Then("pie chart Price section value is {string}")
    public void verifyPieChartPriceSectionValue(String expectedValue) {
        String actualValue = vatCalculatorPage.getPieChartPriceText();
        Assert.assertEquals(
                expectedValue, actualValue, "Price section value doesn't match");
    }

    @Then("pie chart VAT section color is {string}")
    public void verifyPieChartVATFillColor(String expectedColor) {
        String actualColor = vatCalculatorPage.getPieChartVATFillColor();
        Assert.assertEquals(expectedColor, actualColor, "VAT section color doesn't match");
    }

    @Then("user gets vat rate list: {string}")
    public void verifyVatRates(String vatRates) {
        List<String> expectedRates = stringService.getListFromTextSeparatedBySpaces(vatRates);
        List<String> actualRates = vatCalculatorPage.getVatRateLabels();
        Assertions.assertThat(actualRates).containsExactlyInAnyOrderElementsOf(expectedRates);
    }


    @Then("pie chart Price section color is {string}")
    public void verifyPieChartPriceFillColor(String expectedColor) {
        String actualColor = vatCalculatorPage.getPieChartPriceFillColor();
        Assert.assertEquals(expectedColor, actualColor, "VAT section color doesn't match");
    }

    @When("user press Reset button")
    public void pressReset() {
        vatCalculatorPage.pressResetButton();
    }

    @Then("country is {string}")
    public void verifyCountry(String country) {
        String actualColor = vatCalculatorPage.getSelectedCountry();
        Assert.assertEquals(actualColor, country, "Country doesn't match");
    }


}
