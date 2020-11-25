Feature: Dashboard: Activity Overview

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4579")
  Scenario: Dashboard:: Chats waiting in a queue increase in case agent not available
    And User select Standard Billing tenant
    And I login as admin of Standard Billing
    When Click chat icon
    And I open portal
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Activity Overview dashboard tab
