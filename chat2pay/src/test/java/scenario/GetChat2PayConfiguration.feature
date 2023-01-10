Feature: Get Chat 2 pay configuration
#This API returns the configuration data for the configured Chat 2 Pay product.

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4317")
  Scenario Outline: chat2pay-service :: GET /chat-2-pay :: User can get Chat 2 Pay configuration

    Given User is logged in to unity
    And User gets widgetId for UC form
    And User gets activation key for widget
    And User get the C2P configuration with status code <o.statusCode> using <i.activationKey> key

    Examples:
      | i.activationKey                  | o.statusCode |
      | 2b502d66f597435e9d2c854dcbc015a3 | 200          |
      | qweerty                          | 401          |
