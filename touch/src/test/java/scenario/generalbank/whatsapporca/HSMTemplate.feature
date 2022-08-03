@no_widget
Feature: HSMTemplate

  @orca_api
  @start_orca_server
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-105014")
  Scenario: CD :: Agent Desk :: HSM :: Verify if an agent can initiate a chat by sending an HSM template to a WhatsApp via ORCA user
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    And agent click Whatsapp message icon button on the top bar
    And agent fill the customer contact number 919762628065
    When Agent select "Welcome Notification" in Chanel Template
    Then insert the Variable type "Ravindra A" for template
    Then click "send" chat button
    And Agent has new conversation request from orca user
    Then Agent click on new conversation
    Then Conversation area becomes active with connect to agent user's message in it for agent
    When Agent responds with hello to User
    Then Verify Orca returns hello response during 40 seconds

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118469")
  Scenario: CD:: Agent Desk :: Closed Chat :: HSM :: Verify if agent can initiate a chat with WhatsApp via orca user by sending HSM template from Closed tab
    Given I login as agent of General Bank Demo
    And Agent select "Closed" tab from left menu
    Then Admin should see WhatsApp chart in Live Chats by Channel
    And Agent should select the chat that was ended 24hrs ago
      And Chat with history messages is displayed
      And Agent click on 'start chat' button
      And “Continue chat" pop-up window is open
      When Agent select HSM template on the drop-down
      And Fill entries
      And User click 'Send" button
      And And message delivered to a WhatsApp via orca user
      And WhatsApp via orca user reply on a message
      And Agent has new conversation request
      And Agent click on new conversation request
      Then Conversation area becomes active with user's message

      @TestCaseId("https://jira.clickatell.com/browse/TPORT-118472")
      Scenario: CD :: Supervisor Desk :: HSM :: Verify if supervisor can login as agent and send a direct WhatsApp via orca message via message icon on the top bar
        Given user logs in to supervisor desk
        And opens agent desk by clicking launch agent desk
        And Agent click message Icon button on the top bar
        And fill out required fields
        When click on button Send
        Then Message delivered to WhatsApp via orca user
        And user can see the message from supervisor

        @TestCaseId("https://jira.clickatell.com/browse/TPORT-118475")
        Scenario: CD :: Agent Desk :: HSM :: Verify if Agent send HSM template to WhatsApp via orca user then HSM label is displayed in the conversation area
          Given I login as a test agent
          And Agent click message Icon button on the top bar
          And fill out required fields
          When click on button Send
          And Message delivered to A WhatsApp via orca user
          And User can see message with HSM label
          And user reply on a message
          And Agent has new conversation request
          And Agent click on new conversation request
          And Conversation area becomes active with user's message
          Then Agent can see message with HSM label in Conversation area

