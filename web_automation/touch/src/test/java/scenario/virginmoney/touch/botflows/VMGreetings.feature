Feature: VM flow regarding greetings messages

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  @Issue("https://jira.clickatell.com/browse/TPLAT-4185")
  Scenario Outline: VM greetings flow should work for "<user message>" user message
    When User enter <user message> into widget input field
    Then User have to receive 'Hi ${firstName}. Is there something we can help you with?' text response for his '<user message>' input
    Examples:
      |user message       |
      |Hi                 |
      |Hello              |


