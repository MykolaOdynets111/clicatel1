@no_widget
Feature: Apple Business Chat :: Supervisor Desk

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45498")
  Scenario: Supervisor desk:: verify that supervisor is able to check apple live chats
    Given Setup ORCA integration for General Bank Demo tenant
    When Send chat to agent message by ORCA
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    Then Supervisor can see orca live chat with chat to agent message to agent

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45504")
  Scenario: supervisor desk:: Verify if supervisor can filter closed chat by apple business chat channel
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    When Agent select "Closed" left menu option
    And Agent select "Apple Business Chat" in Chanel container and click "Apply filters" button
    Then Verify that only "apple_business_chat" closed chats are shown

#  @agent_support_hours
  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45507")
  Scenario: Supervisor desk:: Verify if supervisor can filter tickets by ticket status and apple channel filter option
    Given Setup ORCA integration for Standard Billing tenant
#    And Set agent support hours with day shift
    When Send to agent message by ORCA
#    Given I open portal
#    And Login into portal as an admin of General Bank Demo account
#    And I select Touch in left menu and Supervisor Desk in submenu
#    When Agent select "Tickets" left menu option
#    And Agent search chat orca on Supervisor desk
#    Then Ticket from orca is present on All tickets filter page
#    And Select orca ticket checkbox
#    When Click 'Assign manually' button
#    Then 'Assign chat' window is opened
#    When I assign chat on Agent
#    And User select Assigned ticket type
#    Then Ticket from orca is present on Assigned filter page
#    When Agent select "Apple Business Chat" in Chanel container and click "Apply filters" button
#    Then Verify that only "apple_business_chat" tickets chats are shown