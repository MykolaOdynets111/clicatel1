@TestCaseId("https://jira.clickatell.com/browse/TPORT-5968")
Feature: General Send OTP flow

  @Issue("https://jira.clickatell.com/browse/TPORT-10789")
  Scenario: Send OTP for new user
    Given sms integration status is set to enabled for Standard Billing tenant
    Given I login as agent of Standard Billing
    Given User with phone number select Standard Billing tenant
    And Click chat icon
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    And Conversation area becomes active with connect to agent user's message
    Then Agent see his phone number added into User profile
    When Agent click on 'Send OTP' button in User profile
    Then 'Verify phone' window is opened
    And User's profile phone number displayed in 'Verify phone' input field
    When Agent click on Send OTP button on 'Verify phone' window
    Then 'Verify phone' window is closed
    And Chat separator with OTP code and 'I have just sent...' message with user phone number are displayed
    And Verify button is displayed in User profile
    And Re-send OTP button is displayed in User profile
    When User refreshes the widget page
    And Click chat icon
    Then There is no OTP code response