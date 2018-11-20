#@smoke
#Feature: User should be asked personal info before redirecting to the agent
#
#
#  Scenario: Verify if user is able to communicate with agent by typing "<user_message>" into widget
#    Given I login as agent of General Bank Demo
#    Given User select General Bank Demo tenant
#    And Click chat icon
#    When User enter chat to agent into widget input field
#    Then Card with a Before I transfer you, please give us some basic info: text is shown on user chat to agent message
#    And User is able to provide some info about himself in the card after his chat to agent message
#
#    Then Agent has new conversation request
#    When Agent click on new conversation request from touch
#    Then Conversation area becomes active with <user_message> user's message
#    And There is no more than one from user message
#    And There is no from agent response added by default for <user_message> user message
#    When Agent responds with hello to User
#    Then User have to receive 'hello' text response for his '<user_message>' input
