@no_widget
Feature: Dashboard: Settings: Business Profile : Agent Support Hours

  @Regression
  @agent_support_hours
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1172")
  Scenario: CD :: Dashboard :: Settings :: Business Profile :: Verify that the default time configuration for all departments is Mon-Sun 00:00 - 23:59

    Given I login as agent of Standard Billing
    And I select Touch in left menu and Dashboard in submenu
    And Navigate to Business Profile page
    When Select Business support hours for all agents option in Agent Supported Hours section
    Then Verify 'Support hours' is default for Standard Billing

  @Regression
  @agent_support_hours
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2312")
  Scenario: Verify if agent can change support hours

    Given I login as agent of Standard Billing
    And I select Touch in left menu and Dashboard in submenu
    And Navigate to Business Profile page
    When Select Business support hours for all agents option in Agent Supported Hours section
    Then Uncheck current day and verify that 'Today' is unselected for Standard Billing
    #    cleanup
    Then Set default 'Support Hours' value for Standard Billing

