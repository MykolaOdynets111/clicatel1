@no_widget
@Regression
Feature: Last agent switch

  Background:
    Given I open portal
    When Login into portal as an admin of Standard Billing account
    And Turn off the Last Agent routing

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1159")
  Scenario: CD :: Dashboard :: Settings :: Preferences :: Verify if supervisor can switch the toggle on or off for the "last agent routing”
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Preferences page
    And Switch Last Agent routing
    Then Verify Last Agent routing is turned On on backend
    And Switch Last Agent routing
    Then Verify Last Agent routing is turned Off on backend