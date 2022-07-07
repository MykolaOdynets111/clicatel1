@no_widget
Feature: WhatsApp ORCA :: Supervisor Desk

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118510")
  Scenario: Supervisor desk:: verify that supervisor is able to check WhatsApp chats
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send chat to agent message by ORCA
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    Then Supervisor can see orca live chat with chat to agent message to agent

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118511")
  Scenario: Supervisor desk:: Verify if supervisor can filter closed chat by WhatsApp chat channel
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    When Agent select "Closed" left menu option
    And Agent select "WhatsApp" in Chanel container and click "Apply filters" button
    Then Verify that only "whatsapp" closed chats are shown

  @agent_support_hours
  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118512")
  Scenario: Supervisor desk:: Verify if supervisor can filter tickets by ticket status and WhatsApp filter option
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And Set agent support hours with day shift
    When Send to agent message by ORCA
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Supervisor Desk in submenu
#    ToDo added wait till issue with filter will be resolved
    And Wait for 10 second
    When Agent select "Tickets" left menu option
    And Agent search chat orca on Supervisor desk
    Then Ticket from orca is present on All tickets filter page
    And Select orca ticket checkbox
    When Click 'Assign manually' button
    Then 'Assign chat' window is opened
    When I assign chat on Agent
    And User select Assigned ticket type
    Then Ticket from orca is present on Assigned filter page
    When Agent select "WhatsApp" in Chanel container and click "Apply filters" button
    Then Verify that only "whatsapp" tickets chats are shown