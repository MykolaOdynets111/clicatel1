@start_orca_server
@Regression
@orca_api
Feature: Dashboard: Settings: Preferences: Route to Specific Departments

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1304")
  @remove_dep
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

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2094")
  Scenario: CD :: Dashboard :: Settings :: Preferences :: Verify that chat is assigned randomly between agents if 'Random chat distribution' option is selected

    Given Setup ORCA Whatsapp integration for Standard Billing tenant
    And routingType tenant feature is set to RANDOM for Standard Billing

    When I login as agent of Standard Billing
    Then Agent changes status to: Unavailable
    And Send connect to Support message by ORCA
    When I login as second agent of Standard Billing
    Then Second agent has new conversation request

    When Second agent changes status to: Unavailable
    When Agent changes status to: Available
    When Send 1 messages Hi by ORCA
    Then Agent has new conversation request




