@without_tct
Feature: General Send OTP flow
  Scenario: Send OTP for new user
    Given sms integration status is set to enabled for Standard Billing tenant
    Given I login as agent of Standard Billing
    Given User with phone number select Standard Billing tenant
    And Click chat icon
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Agent see his phone number added into customer's profile
    When Agent click on 'Send OTP' button in Customer 360
    Then 'Verify phone' window is opened
    And User's profile phone number displayed in 'Verify phone' input field
    When Agent click on Send OTP button on 'Verify phone' window
    Then 'Verify phone' window is closed
    And Chat separator with OTP code and 'I have just sent...' message with user phone number are displayed
    And Verify button is displayed in Customer 360
    And Re-send OTP button is displayed in Customer 360
    When User refreshes the widget page
    And Click chat icon
    Then There is no OTP code response