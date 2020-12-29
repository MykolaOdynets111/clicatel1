@no_widget
@orca_api
Feature: Apple Business Chat :: Dashboard

  Background:
    Given Setup ORCA integration for General Bank Demo tenant

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45501")
  Scenario: Dashboard:: Verify if Apple channel is displayed on live customer charts
    When Send chat to agent message by ORCA
    And I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Live Customers on dashboard
    Then Admin should see Apple Business Chat chart in Live Chats by Channel
    And Admin should see Apple Business Chat charts in General sentiment per channel
    And Admin should see Apple Business Chat charts in Attended vs. Unattended Chats

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45509")
  Scenario: Dashboard: ABC support: Verify customer overview displays current number of Live Chats being engaged by agents for ABC.
    When Send chat to agent message by ORCA
    And I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Live Customers on dashboard
    Then Admin should see Apple Business Chat chart in Live Chats by Channel
    And Verify admin can see number of live chats per channel when hover over abc

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45506")
  Scenario: Dashboard: ABC support: Verify Customer overview displays correct report of Attended / Unattended chats
    Given I login as agent of General Bank Demo
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    When Agent responds with hello to User
    When Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Live Customers on dashboard
    Then Verify admin can see number of attended chats when hover over Apple Business Chat channel

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45503")
  Scenario: Dashboard: ABC support: Verify Customer overview displays correct general sentiment for Apple business Chat
    Given I login as agent of General Bank Demo
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    When Agent responds with hello to User
    When Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Live Customers on dashboard
    Then Verify admin can see number of positive sentiment chats when hover over Apple Business Chat channel