@start_orca_server
Feature: Dashboard: Settings: Preferences: Route to Specific Departments

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1304")
  @remove_dep
  @Regression
  Scenario: CD :: Dashboard :: Department Management :: Verify if Whatsapp chat "Route to Specific Departments" when department agent is online

    Given I login as agent of Standard Billing
    And New departments with Auto_Department name Auto_Description description and agent is created
    And Setup ORCA whatsapp integration for Standard Billing tenant
    And Turn off the Default department

    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    Then Select Auto_Department department By Default

    When Agent switches to opened Portal page
    And I select Touch in left menu and Supervisor Desk in submenu
    And Send Hi message by ORCA to Auto_Department department
    Then Agent has new conversation request from orca user
