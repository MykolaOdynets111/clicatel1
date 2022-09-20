@no_widget
Feature: Dashboard: Settings: Business Profile : Agent Support Hours

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-76362")
  @Regression
  @agent_support_hours
  Scenario: CD :: Dashboard :: Settings :: Business Profile :: Verify that the default time configuration for all departments is Mon-Sun 00:00 - 23:59

    Given I login as agent of Standard Billing
    And I select Touch in left menu and Dashboard in submenu
    And Navigate to Business Profile page
    When Select Business support hours for all agents option in Agent Supported Hours section
    Then Verify 'Support hours' are default for Standard Billing

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-5317")
  @Regression
  @agent_support_hours
  Scenario: CD :: Dashboard :: Settings :: Business Profile :: Verify if supervisor can change support hours

    Given I login as agent of Standard Billing
    And I select Touch in left menu and Dashboard in submenu
    And Navigate to Business Profile page
    When Select Business support hours for all agents option in Agent Supported Hours section
    Then Uncheck current day and verify that 'Today' is unselected for Standard Billing
    #    cleanup
    Then Set default 'Support Hours' value for Standard Billing