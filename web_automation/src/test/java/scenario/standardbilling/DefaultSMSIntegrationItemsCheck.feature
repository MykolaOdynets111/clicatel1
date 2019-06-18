Feature: Default SMS integration items visibility check

  Background:
    Given I open portal
    Given Login into portal as an admin of Standard Billing account
    Given I select Touch in left menu and Configure Touch in submenu
    And Enable the SMS integration
    Then Status of SMS integration is changed to "Active"
    And I select Touch in left menu and Launch Chat Desk in submenu
    Given User select Standard Billing tenant
    And Click chat icon
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to agent user's message

  Scenario: Send SMS button change state depending on phone number availability
    When Agent see no phone number added into customer's profile
    Then Send SMS button is disabled for the Agent
    And Send OTP button is displayed
    When Click 'Edit' button in Customer 360 view
    And Change phone number for touch user and save changes
    Then Send SMS button is enabled for the Agent
    And Send OTP button is displayed
