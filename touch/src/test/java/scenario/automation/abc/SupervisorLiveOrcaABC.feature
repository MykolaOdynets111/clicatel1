@Regression
@orca_api
Feature: ABC ORCA :: Supervisor Desk

  @support_hours
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2848")
  Scenario: Supervisor desk:: Verify if supervisor can filter tickets by ticket status and apple channel filter option
  Given Setup ORCA abc integration for Automation tenant
  And Set agent support hours with day shift
  When Send to agent message by ORCA
  Given I open portal
  And Login into portal as an admin of Automation account
  And I select Touch in left menu and Supervisor Desk in submenu
  When Agent select "Tickets" left menu option
  And Agent search chat orca on Supervisor desk
  Then Agent see tickets from orca on Unassigned filter page
  And Select orca ticket checkbox
  When Click 'Assign manually' button for orca
  Then 'Assign chat' window is opened
  When I assign chat on Agent for Agent dropdown
  And Agent select Assigned filter on Left Panel
  Then Agent see tickets from orca on Assigned filter page
  When Agent select "Apple Business Chat" in Chanel container and click "Apply filters" button
  Then Agent verify that only "apple-business-chat" tickets chats are shown

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1981")
  Scenario: CD :: Supervisor Desk :: Chats :: Verify the Chats that are currently in the 'Pending' tab will have a yellow 'Pending' icon on them in the Supervisor view
    Given Setup ORCA abc integration for Automation tenant
    When I login as agent of Automation
    And Send to agent message by ORCA
    And Agent has new conversation request from ORCA user
    And Agent click on new conversation request from ORCA
    And Agent click 'Pending' chat button
    And I select Touch in left menu and Supervisor Desk in submenu
    And Agent search chat orca on Supervisor desk
    And Agent click On Live Supervisor Desk chat from ORCA channel
    Then Verify Chat has pending icon in the Chat List
    Then Verify Chat has pending icon in the Chat View

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2082")
   Scenario: CD :: Supervisor Desk :: Chats :: Verify that the "All live chats" card has both "pending and live chats" in Supervisor view
    Given Setup ORCA abc integration for Automation tenant
    When I login as agent of Automation
    And Send live chat message by ORCA
    And I select Touch in left menu and Supervisor Desk in submenu
    And Agent search chat orca on Supervisor desk
    And Agent select All live chats filter on Left Panel
    Then Agent click On Live Supervisor Desk chat from ORCA channel
    And I select Touch in left menu and Agent Desk in submenu
    And Agent has new conversation request from ORCA user
    And Agent click on new conversation request from ORCA
    And Agent click 'Pending' chat button
    And I select Touch in left menu and Supervisor Desk in submenu
    And Agent search chat orca on Supervisor desk
    And Agent select All live chats filter on Left Panel
    And Agent click On Live Supervisor Desk chat from ORCA channel
    Then Verify Chat has pending icon in the Chat List