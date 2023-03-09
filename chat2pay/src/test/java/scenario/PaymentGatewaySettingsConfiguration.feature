Feature: Channels Management

  Background:
    Given User is logged in to unity
    When User creates AQA widget

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4741")
  Scenario Outline: C2P Unity API :: Payment Gateway Settings Configuration :: GET /payment-gateway-settings :: truth table

    And User sets up 'Unified Payment Setting' for widget
    Then User gets 'Unified Payment Gateway Settings'
      | i.widgetId         | <i.widgetId>         |
      | o.paymentGatewayId | <o.paymentGatewayId> |
      | o.locale           | <o.locale>           |
      | o.country          | <o.country>          |
      | o.defaultCurrency  | <o.defaultCurrency>  |
      | o.responseCode     | <o.responseCode>     |
      | o.errors           | <o.errors>           |
      | o.path             | <o.path>             |

    Examples:
      | i.widgetId | o.paymentGatewayId | o.locale         | o.country | o.defaultCurrency | o.responseCode | o.errors  | o.path                                       |
      | valid      | 1                  | English (Canada) | Canada    | South Africa      | 200            |           |                                              |
      | invalid    |                    |                  |           |                   | 404            | NOT_FOUND | URL /v2/widget/null/payment-gateway-settings |