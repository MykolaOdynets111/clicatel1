# Created by modynets at 15.12.2022
Feature: Transaction execution
  # user can get the transaction link and is able to make payment (positive and negative)

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4317")
  Scenario: chat2pay-service :: POST /chat-2-pay :: User can get a payment link
    Given User fetch token and accountID for an existed account
    When User is logged in to unity
    And User gets widgetId for UC form
    And User gets paymentGatewaySettingsId for widget
    And User gets application Id for widget
    And User gets activation key for widget
    Then User can get a correct payment link








