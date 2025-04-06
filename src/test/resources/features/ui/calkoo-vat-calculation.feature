@ui
Feature: Calkoo VAT Calculation

  Background:
    Given user navigates to "http://www.calkoo.com/?lang=3&page=8"

  @tc01
  Scenario Outline: VAT Calculation with Price without VAT Input
    When user selects a country: "<country>"
    And user selects a VAT rate: "<VAT_rate>"
    And user selects "Price without VAT" as the input type
    Then input: "Price without VAT" is "empty"
    And input filed explanation for "Price without VAT" displaying: "<Price_without_VAT_explanation>"
    And input: "Value-Added Tax" is "NaN"
    And input: "Value-Added Tax" has class "disabled"
    When input: "Value-Added Tax" is edited with value: "1489"
    Then input: "Value-Added Tax" is "NaN"
    Then input: "Price incl. VAT" is "NaN"
    And input filed explanation for "Price incl. VAT" displaying: "<Price_incl_VAT_explanation>"
    And input: "Price incl. VAT" has class "disabled"
    When input: "Price incl. VAT" is edited with value: "1489"
    Then input: "Price incl. VAT" is "NaN"
    When input: "Price without VAT" is edited with value: "<Price_without_VAT_entered_value>"
    Then input: "Price without VAT" is "<Price_without_VAT_value>"
    And input: "Value-Added Tax" is "<Value_Added_Tax_value>"
    And input: "Price incl. VAT" is "<Price_incl_VAT_value>"
    And pie chart VAT section color is "#b94a02"
    And pie chart VAT section value is "<pie_chart_vat_value>"
    And pie chart Price section color is "#1483ba"
    And pie chart Price section value is "<pie_chart_price_value>"

    Examples:
      | country  | VAT_rate         | Price_without_VAT_explanation | Price_incl_VAT_explanation  | Price_without_VAT_entered_value  | Price_without_VAT_value | Value_Added_Tax_value  | Price_incl_VAT_value| pie_chart_vat_value | pie_chart_price_value|
      | Austria  | 10%              | 0.100000                      | 0.090909                    | 100.000                          | 100.000                 | 10.00                  | 110.00              | 9.1%                | 90.9%                |

   #   Same Scenario for @tc02 and tc03

  @tc05
  Scenario Outline: Country - vat rates mapping
    When user selects a country: "<country>"
    Then user gets vat rate list: "<VAT_rate_list>"

    Examples:
      | country         | VAT_rate_list           |
      | Austria         | 10% 13% 20%             |
      | France          | 2.1% 5.5% 10% 20%       |
      | United Kingdom  | 5% 20%                  |

  @tc07
  Scenario Outline: Value Added Tax Calculator Reset
    When user selects a country: "<country>"
    And user selects a VAT rate: "<VAT_rate>"
    And user selects "Price incl. VAT" as the input type
    And input: "Price incl. VAT" is edited with value: "<Price_incl_VAT_entered_value>"
    Then input: "Price without VAT" is "<Price_without_VAT_value>"
    And input: "Value-Added Tax" is "<Value_Added_Tax_value>"
    When user press Reset button
    Then country is "<country>"
    And input: "Price without VAT" is "empty"
    And input: "Value-Added Tax" is "empty"
    And input: "Price incl. VAT" is "empty"

    Examples:
      | country  | VAT_rate        | Price_incl_VAT_entered_value  | Price_without_VAT_value | Value_Added_Tax_value  | Price_without_VAT_value|
      | Austria  | 10%             | 110.00                        | 100.00                 | 10.00                  | 100.00                |


