@tie
Feature: VM: TIE sentiments and intent for card activation questions

  Scenario Outline: Verify if TIE sentiment is NEUTRAL or POSITIVE for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for Virgin Money tenant
    And TIE returns 1 intent: "card activation" on '<user_message>' for Virgin Money tenant

    Examples:
      |user_message                             |
      |How do I activate my card                |
      |What is the card activation process      |
      |What must I do to activate my card       |
      |What do you need to activate my card     |
      |What documents do I need to fica my card |