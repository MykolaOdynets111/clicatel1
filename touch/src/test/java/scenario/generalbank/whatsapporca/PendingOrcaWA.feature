@no_widget
Feature: Whatsapp ORCA :: Chatdesk

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-105432")
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
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-105434")
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
    And Agent click on new conversation request from orca
    Then Agent view the visual indicator "This chat has been marked as live chat by " agent name and timestamp in the conversation area