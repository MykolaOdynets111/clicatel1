@no_widget
Feature: Apple Business Chat :: Supervisor Desk

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2844")
  @Regression
  Scenario: Supervisor desk:: verify that supervisor is able to check apple live chats
    Given Setup ORCA abc integration for General Bank Demo tenant
    When Send chat to agent message by ORCA
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    Then Supervisor can see orca live chat with chat to agent message to agent

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1882")
  @Regression
  Scenario: supervisor desk:: Verify if supervisor can filter closed chat by apple business chat channel
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    When Agent select "Closed" left menu option
    And Agent select "Apple Business Chat" in Chanel container and click "Apply filters" button
    Then Verify that only "apple-business-chat" closed chats are shown

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-106817")
  Scenario: Supervisor Desk :: Verify if the first view on supervisor desk is ‘Chats’ tab
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    Then Verify that Chat is displayed first

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-106827")
  Scenario: Supervisor Desk :: Closed Chat :: Verify if roster is updated after a chat is closed on supervisor desk
    Given I login as Second Agent of General Bank Demo
    And Setup ORCA abc integration for General Bank Demo tenant
    And Send chat to be closed message by ORCA
    Then Second Agent has new conversation request from orca user
    And Second Agent click on new conversation request from orca
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Supervisor Desk in submenu
    And Agent click On Live Supervisor Desk chat from Orca channel
    And Second Agent click "End chat" button
    Then Admin clicks 'Go to chat' button
    And Orca request is shown on Supervisor Desk Live page

  @orca_api
  @regression
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1325")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1315")
  Scenario: Supervisor Desk :: Chats :: Transfer Chat :: Verify if agent name is updated after chat is being transferred from agent1 to agent2 on header
    Given Setup ORCA abc integration for General Bank Demo tenant
    And I login as Agent of General Bank Demo
    And Send chat for transfer message by ORCA
    Then Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And I login as Second Agent of General Bank Demo
    When Agent transfers chat
    And Second Agent click "Accept transfer" button
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    Then Supervisor clicks on chats filter for GBD Second Agent
    And Agent click On Live Supervisor Desk chat from Orca channel
    Then GBD Second is the current agent of the chat