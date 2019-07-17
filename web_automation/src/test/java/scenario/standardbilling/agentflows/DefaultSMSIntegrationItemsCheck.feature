Feature: Default SMS integration items visibility check

  Scenario: SMS integration related buttons are disappearing after disabling SMS integration
    Given I open portal
    Given Login into portal as an admin of Standard Billing account
    Given I select Touch in left menu and Configure Touch in submenu
    And Enable the SMS integration
    Then Status of SMS integration is changed to "Active"
    When I launch chatdesk from portal
    Given User select Standard Billing tenant
    And Click chat icon
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Send SMS button is disabled on Chat header
    And Send OTP button is displayed in Customer 360
    When Agent switches to opened Portal page
    And Disable the SMS integration
    Then Status of SMS integration is changed to "Not Active"
    When Agent switches to opened ChatDesk page
    And Agent refresh current page
    Then Agent has old conversation shown
    And Agent click on last opened conversation request from touch
    And Send SMS button hidden from the Chat header
    And Send OTP button not displayed in Customer 360

  Scenario: Send SMS button change state depending on phone number availability
    Given sms integration status is set to enabled for Standard Billing tenant
    Given I login as agent of Standard Billing
    Given User select Standard Billing tenant
    And Click chat icon
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    When Agent see no phone number added into customer's profile
    Then Send SMS button is disabled on Chat header
    And Send OTP button is displayed in Customer 360
    When Click 'Edit' button in Customer 360 view
    And Change phone number for touch user
    And Click 'Save' button in Customer 360 view
    When Wait for 3 seconds for Phone Number to be updated
    Then Send SMS button is enabled on Chat header
    And Send OTP button is displayed in Customer 360