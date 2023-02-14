Feature: Dashboard: Settings: Preferences

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1356")
  @Regression
  Scenario: CD :: Dashboard :: Settings :: Preferences :: Verify the default value in "pending chats auto-closure time" section is displayed between 1 to 24 hours for new client

    Given I login as agent of Standard Billing
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Preferences page
    Then Verify 'Pending Chats Auto-closure Time' is between 1 and 24 hours
