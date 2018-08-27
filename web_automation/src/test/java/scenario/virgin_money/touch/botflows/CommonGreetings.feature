Feature: VM flow regarding Common greetings messages

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  Scenario Outline: Common greetings flow should work for "<user message>" user message
    When User enter <user message> into widget input field
#    Then User have to receive 'Hi ${firstName} is there something we can help you with?' text response for his '<user message>' input
    Then User have to receive 'Hello' text response for his '<user message>' input
    Examples:
      |user message       |
      |Hi                 |
      |Hello              |


