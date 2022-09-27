@no_widget
@Regression
Feature: Supervisor desk

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2787")
  Scenario: CD::Supervisor desk :: Verify if supervisor is able to check live chats
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    When I login as agent of General Bank Demo
    And Setup ORCA whatsapp integration for General Bank Demo tenant
    And Send to agent message by ORCA
    When I select Touch in left menu and Supervisor Desk in submenu
    When Verify "All Chats" display default
    Then  Verify that live chats available are shown
