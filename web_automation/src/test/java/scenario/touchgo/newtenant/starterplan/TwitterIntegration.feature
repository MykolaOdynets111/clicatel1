Feature: Creating integration with Twitter

  Background:
#    Given New tenant is successfully created

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-3673)
  Scenario: Admin of the Starter tenant should be able to set up twitter integration
    Given I open portal
    And Login into portal as an admin of Updating AQA account
#    And Login into portal as an admin of SignedUp AQA account
    When I select Touch in left menu and Configure Touch in submenu
    Then Status of Twitter mentions & DM integration is "Not setup" in integration card
    When Click 'Configure' button for Twitter mentions & DM integration
    And Add twitter integration with twitter page
    Then Status of Twitter mentions & DM integration is "Active" in integration card
    And Twitter integration is saved on the backend
    And Delete Twitter integration on the backend
