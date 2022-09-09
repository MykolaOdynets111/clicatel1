@no_widget
Feature: Supervisor desk

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2787")
  @Regression
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

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1168")
  @Regression
  Scenario: CD :: Supervisor Desk :: Chats :: Verify if the first view on supervisor desk is ‘Chats’ tab
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    Then Verify that Chats tab is displayed first