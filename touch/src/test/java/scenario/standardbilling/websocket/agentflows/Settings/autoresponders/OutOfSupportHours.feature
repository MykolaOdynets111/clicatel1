@no_widget
@Regression
@start_orca_server
@Regression
Feature: Dashboard: Settings: Auto Responder

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1915")
  @agent_support_hours
  Scenario: CD :: Dashboard :: Settings :: Auto Responder :: Verify that customers receives the 'out of support hours' message when tries to reach agent out of business hours

    Given Setup ORCA whatsapp integration for Standard Billing tenant
    And Set agent support hours with day shift
    When Send to agent message by ORCA
    Then Verify Orca returns Out of Support Hours message autoresponder during 40 seconds

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1822")
  @remove_dep
  @department_support_hours
  Scenario: CD :: Dashboard :: Settings :: Auto Responder :: Verify that user should receive"out of support hours"
  autoresponder message when user try to reach out of specific support hours per department time

    Given I login as agent of Standard Billing
    And Setup ORCA whatsapp integration for Standard Billing tenant
    And New departments with Auto_Department name Auto_DepartmentDescription description and agent is created
    And Turn off the Default department

    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    Then Select Auto_Department department By Default
    Then Set for Auto_Department department 'Support Hours and days' with day shift

    When Agent switches to opened Portal page
    And I select Touch in left menu and Agent Desk in submenu
    And Send to agent message by ORCA to Auto_Department department
    Then Verify Orca returns Out of Support Hours message autoresponder during 40 seconds
