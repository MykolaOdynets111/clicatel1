@no_widget
@Regression
Feature: Whatsapp ORCA :: Chatdesk

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1939")
  Scenario: CD :: Agent Desk :: Pending Chat :: Verify that visual indicator appears in the conversation area if Agent marks chat as Pending
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Agent click 'Pending' chat button
    Then Agent receives pending message with orca user name
    When Agent select "Pending" left menu option
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Agent view the visual indicator "This chat has been marked as Pending by " agent name and timestamp in the conversation area

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2002")
  Scenario: CD :: Agent Desk :: Pending Chat :: Verify that visual indicator appears in the conversation area if Agent marks chat as unpending
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Agent click 'Pending' chat button
    Then Agent receives pending message with orca user name
    When Agent select "Pending" left menu option
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Agent view the visual indicator "This chat has been marked as Pending by " agent name and timestamp in the conversation area
    When Agent click 'Pending On' chat button
    When Agent select "Live Chats" left menu option
    And Agent click on new conversation request from orca
    Then Agent view the visual indicator "This chat has been marked as live chat by " agent name and timestamp in the conversation area

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2010")
  Scenario: CD :: Agent Desk :: Pending Chat :: Verify that visual indicator appears in the conversation area if chat moves from Pending to Live based on a reply from the end-user
    Given I login as agent of General Bank Demo
    And Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Conversation area becomes active with connect to Support user's message
    And Agent click 'Pending' chat button
    And Agent receives pending message with orca user name
    And Agent select "Pending" left menu option
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    Then Agent view the visual indicator "This chat has been marked as Pending by " agent name and timestamp in the conversation area
    When Send move from pending to live chat message by ORCA
    And Agent clicks on go to chat button
    And Agent select "Live Chats" left menu option
    And Agent click on new conversation request from orca
    Then Agent checks visual indicator with text The user has responded, the chat has now been marked as live chat is shown during 2 seconds