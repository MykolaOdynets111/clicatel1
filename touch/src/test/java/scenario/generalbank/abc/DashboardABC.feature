@no_widget
@orca_api
@Regression
Feature: Apple Business Chat :: Dashboard

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2301")
  Scenario: Dashboard:: Verify if Apple channel is displayed on live customer charts
    Given Setup ORCA abc integration for General Bank Demo tenant
    When Send chat to agent message by ORCA
    And I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Live Customers on dashboard
    Then Admin should see Apple Business Chat chart in Live Chats by Channel
    And Admin should see Apple Business Chat charts in General sentiment per channel
    And Admin should see Apple Business Chat charts in Attended vs. Unattended Chats
    Then Send //end message by ORCA

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2399")
  Scenario: Dashboard: ABC support: Verify customer overview displays current number of Live Chats being engaged by agents for ABC.
    Given Setup ORCA abc integration for General Bank Demo tenant
    When Send chat to agent message by ORCA
    And I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Live Customers on dashboard
    Then Admin should see Apple Business Chat chart in Live Chats by Channel
    And Verify admin can see number of live chats per channel when hover over abc
    Then Send //end message by ORCA

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2298")
  Scenario: Dashboard: ABC support: Verify Customer overview displays correct report of Attended / Unattended chats
    Given I login as agent of General Bank Demo
    Given Setup ORCA abc integration for General Bank Demo tenant
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
    Then Send //end message by ORCA

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2281")
  Scenario: Dashboard: ABC support: Verify Customer overview displays correct general sentiment for Apple business Chat
    Given I login as agent of General Bank Demo
    Given Setup ORCA abc integration for General Bank Demo tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    When Agent responds with hello to User
    When Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Live Customers on dashboard
    Then Verify admin can see number of chats when hover over Apple Business Chat channel