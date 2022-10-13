@remove_dep
@skip
#Only for checking default value. Should be run only after tenant creation
Feature: Dashboard: Settings: Preferences: Timers & Timeouts

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2043")
  Scenario: CD :: Dashboard :: Settings :: Preferences :: Verify if the default value of "Pending Chats Auto-closure Time" is 8 hours
    Given I login as agent of Standard Billing
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Preferences page
    Then Verify 'Pending Chats Auto-closure Time' is 8 hours
