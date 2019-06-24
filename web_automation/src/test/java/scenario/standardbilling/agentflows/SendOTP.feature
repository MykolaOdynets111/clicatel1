Feature: OTP functionality
  Scenario: Check Phone number and integration availability
    Given I login as agent of Standard Billing
    Given User with phone number select Standard Billing tenant
    And Click chat icon
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Agent see his phone number added into customer's profile
    When sms integration status is set to disabled for Standard Billing tenant
    And Agent refresh current page
    And Agent click on last opened conversation request from touch
    Then Send OTP button not displayed in Customer 360