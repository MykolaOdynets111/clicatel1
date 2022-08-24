Feature: OTP, Verify and Re-send OTP functionality

  Background:
    Given sms integration status is set to enabled for Standard Billing tenant
    Given I login as agent of Standard Billing
    Given User with phone number select Standard Billing tenant
    Given Agent send OTP message with API
    And Click chat icon
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    And Conversation area becomes active with connect to agent user's message
    Then Agent see his phone number added into User profile

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-5972")
  Scenario: Re-send OTP
    Then 'Verify' and 'Re-send OTP' buttons are displayed in User profile
    When Agent click on 'Re-send OTP' button in User profile
    And Agent click on Send OTP button on 'Verify phone' window
    Then 'Verify phone' window is closed
    And Chat separator with OTP code and 'I have just sent...' message with user phone number are displayed
    And 'Verify' and 'Re-send OTP' buttons are displayed in User profile

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-5971")
  Scenario: Verifying and Editing verified phone number
    Then 'Verify' and 'Re-send OTP' buttons are displayed in User profile
    When Agent click on 'Verify' button in User profile
    Then 'Verified' label become visible
    And SMS client-profile added into DB
    And 'Verify' and 'Re-send OTP' buttons are not displayed in User profile
    When Click 'Edit' button in Profile
    And Change phone number for touch user
    And Click 'Save' button in Profile
    When Wait for 3 seconds for Phone Number to be updated
    Then 'Verified' label become invisible
    And Send OTP button is displayed in User profile

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-5970")
  Scenario: No data in 'Verify phone' pop-up window
    When Click 'Edit' button in Profile
    And Delete phone number for touch user
    And Click 'Save' button in Profile
#    When Wait for 3 seconds for Phone Number to be updated
    When Wait for 2 seconds for Phone Number update
    When Agent click on 'Send OTP' button in User profile
    Then 'Verify phone' window is opened
    And User's profile phone number not displayed in 'Verify phone' input field
    When Agent click on Cancel button on 'Verify phone' window
