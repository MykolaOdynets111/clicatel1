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
    Then Verify that Chats tab is displayed first

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

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1981")
  Scenario: CD :: Supervisor Desk :: Chats :: Verify the Chats that are currently in the 'Pending' tab will have a yellow 'Pending' icon on them in the Supervisor view
    Given I login as agent of General Bank Demo
    When Setup ORCA abc integration for General Bank Demo tenant
    And Set agent support hours for all week
    And Send to agent message by ORCA
    And Agent has new conversation request from ORCA user
    And Agent click on new conversation request from ORCA
    And Agent click 'Pending' chat button
    And I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Supervisor Desk in submenu
    And Agent search chat orca on Supervisor desk
    And Agent click On Live Supervisor Desk chat from ORCA channel
    Then Verify Chat has pending icon in the Chat List
    Then Verify Chat has pending icon in the Chat View