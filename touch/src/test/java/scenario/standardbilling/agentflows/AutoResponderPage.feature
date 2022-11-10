@agent_feedback
@Regression
Feature: AutoResponder

  Background:
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Auto Responders page
    And Wait for auto responders page to load

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2728")
  Scenario:CD:: Dashboard:: Auto Responder:: Reset to default connect_agent auto responder
    And Agent click expand arrow for Connect Agent message auto responder
    And Admin edit the text for Connect Agent message auto responder
    And Admin click save button for Connect Agent message auto responder
    And Click "Reset to default" button for Connect Agent message auto responder
    Then The Connect Agent message Auto Responder message was reset again




