
Feature: Survey Management: Test Main Flow with NSP

  Background:
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | setRatingEnabled | true  |
      | setRatingType    | NPS   |
    And User select Standard Billing tenant
    Given I login as agent of Standard Billing
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19275")
  Scenario: verify if user can 'rate and leave comment' for webchat NPS survey type
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When Agent closes chat

#    When I select Touch in left menu and Touch Preferences in submenu
#    And Click "Survey management" nav button