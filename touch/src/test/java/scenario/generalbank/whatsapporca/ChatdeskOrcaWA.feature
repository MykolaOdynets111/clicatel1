@no_widget
@Regression
Feature: Whatsapp ORCA :: Chatdesk

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1739")
  Scenario: ChatDesk:: The header should have whatsapp icon when user is chatting using orca whatsapp
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    Then Valid image for whatsapp integration are shown in left menu with chat
    And Agent should see whatsappHeader icon in active chat header

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1821")
  Scenario: ChatDesk: ORCA WhatsApp: Verify if //END message works for whatsapp chat
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Send //end message by ORCA
    Then Agent should not see from user chat in agent desk from orca

  @orca_api
  @start_orca_server
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1826")
  Scenario: ChatDesk:: Verify if agent is able to transfer Orca WhatsApp chat via "Transfer chat" button
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    When I login as second agent of General Bank Demo
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    And Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    Then Second agent receives incoming transfer with "Please take care of this one" note from the another agent
    And Second agent can see transferring agent name, orca and following user's message: 'connect to agent'
    When Second agent click "Accept transfer" button
    Then Second agent has new conversation request from orca user
    When Second agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message in it for second agent
    When Second agent responds with hello to User
    Then Verify Orca returns hello response during 40 seconds

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1752")
  Scenario: ChatDesk:: Verify if agent can filter closed chat using WhatsApp chat channel
    Given I login as agent of General Bank Demo
    When Agent select "Closed" left menu option
    When Agent filter closed chats with WhatsApp channel, no sentiment and flagged is false
    Then Agent see only whatsapp chats in left menu

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1162")
  Scenario: CD :: Agent Desk :: Live Chat :: Location :: Verify if agent click on the small cross on search bar, the text entered in the search bar is deleted
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    When Agent open chat location form
    And Agent search for Paris Location
    And Agent click on reset button
    Then Location field becomes empty

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1171")
  Scenario: CD :: Agent Desk :: Live Chat :: Verify if agent can send plain text message to a user over WhatsApp Channel
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Agent send Hello message

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1176")
  Scenario: CD :: Agent Desk :: Live Chat :: Profile :: Verify the text in the "not verified" label is in grey color in customer profile phone number field
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    When Agent click on Edit button in User profile
    And Enter 12345678901 in the phone number field
    And Agent click Save button in User profile
    Then Not verified label is displayed

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1835")
  Scenario: CD :: Agent Desk :: Live Chat :: Verify that location should auto-populate after agent try to search for second location without selecting the first one.
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    When Agent open chat location form
    And Agent search for Paris Location
    And Agent click on Paris Location
    And Agent click on reset button
    Then Location field becomes empty
    And Agent search for Canada Location
    And Agent click on Canada Location

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1165")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1848")
  @orca_api
  Scenario Outline: CD :: Location :: <channelType>:: Verify that An agent should be able to search for any location
    Given I login as agent of General Bank Demo
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from <userType> user
    When Agent click on new conversation request from <userType>
    Then Conversation area becomes active with connect to agent user's message
    When Agent open chat location form
    And Agent search for Paris Location
    And Agent click on Paris Location
    Examples:
      | channelType | userType|
      | SMS         | sms     |
      | Whatsapp    | orca    |

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

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2369")
    @support_hours
  Scenario: CD:: Supervisor desk :: Verify if Supervisor is able to Route ticket to scheduler
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And Set agent support hours with day shift
    When Send to agent message by ORCA
    And I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    And Agent search chat ORCA on Supervisor desk
    Then Ticket from orca is present on All tickets filter page
    And Select orca ticket checkbox
    When Click 'Route to scheduler' button
    Given I login as agent of General Bank Demo
    And Agent select "Tickets" left menu option
    Then Agent has new conversation request from ORCA user

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2108")
  @support_hours
  Scenario: CD:: Supervisor:: Verify if supervisor is able to close "Assign Chat" modal
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Set agent support hours for all week
    And Send to agent message by ORCA
    And I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Supervisor Desk in submenu
    And Agent search chat orca on Supervisor desk
    When Agent click On Live Supervisor Desk chat from ORCA channel
    And Agent click on three dot vertical menu and click on assign button
    When Assign chat modal is opened
    And Agent is able to close the assign chat window

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1129")
  @Regression
  Scenario: CD :: Supervisor Desk :: Live Chat :: Profile :: Verify that WhatsApp profile name is displayed as username on customer profile section
    Given I login as agent of General Bank Demo
    And Setup ORCA whatsapp integration for General Bank Demo tenant
    And Send to agent message by ORCA
    When I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Supervisor Desk in submenu
    And Agent search chat orca on Supervisor desk
    When Agent click On Live Supervisor Desk chat from ORCA channel
    Then Agent can see whatsapp profile name

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1302")
  Scenario: CD :: Supervisor Desk :: Chat :: Chat2Pay :: Verify that supervisor does not have the capability to initiate a payment transaction
    Given I login as agent of General Bank Demo
    When Setup ORCA whatsapp integration for General Bank Demo tenant
    And Send to agent message by ORCA
    And Agent has new conversation request from ORCA user
    And Agent click on new conversation request from ORCA
    And I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Supervisor Desk in submenu
    And Agent search chat orca on Supervisor desk
    And Agent click On Live Supervisor Desk chat from ORCA channel
    Then Agent cannot initiate a payment

  @setting_changes
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2751")
  Scenario: CD :: Chat :: Verify that neutral sentiment is set by default when agent closes a chat
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And agentFeedback tenant feature is set to true for General Bank Demo
    And I login as agent of General Bank Demo
    When Send connect to agent message by ORCA
    And Agent has new conversation request from ORCA user
    And Agent click on new conversation request from ORCA
    When Agent click "End chat" button
    And Agent Feedback popup for agent should be opened
    Then Correct neutral sentiment selected