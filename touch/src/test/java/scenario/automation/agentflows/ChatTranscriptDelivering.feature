@chat_transcript
@without_tct

Feature: Chat Transcript for WebChat

  Scenario: Agent receiving chat transcript after WebChat conversation ends
    Given Set Chat Transcript attribute to ALL for Automation tenant
    Given Clear Chat Transcript email inbox
    Given I login as agent of Automation
    Given User select Automation tenant
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    And Save clientID value for webchat user
    Then Conversation area becomes active with chat to agent user's message
    When Agent responds with check chat transcript email to User
    Then Agent closes chat
    Then Chat Transcript email arrives
    And Email title contains webchat adapter, client ID/Name/Email, chat ID, session number values
    And Email content contains chat history from the terminated conversation