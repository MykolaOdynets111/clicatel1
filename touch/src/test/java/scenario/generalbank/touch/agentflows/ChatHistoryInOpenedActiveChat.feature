Feature: Agent should be able to see chat history in opened active chat

  @Issue("https://jira.clickatell.com/browse/TPORT-25288")
  Scenario: Verify agent can view chat history in opened active chat
    Given User select General Bank Demo tenant
    And Click chat icon
    Given Chat history of the client is available for the Agent of General Bank Demo
    When I login as agent of General Bank Demo
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Agent sees correct chat with basic info in chat history container
    When Agent click 'View chat' button
    Then Agent sees correct messages in history details window

