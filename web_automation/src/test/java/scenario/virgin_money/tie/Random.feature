@tie
Feature: VM: TIE sentiments and intent regarding random interactions questions

  Scenario Outline: Verify if TIE sentiment is NEUTRAL or POSITIVE for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for Virgin Money tenant
    And TIE returns 1 intent: "random" on '<user_message>' for Virgin Money tenant

    Examples:
      |user_message       |
      |Thumbs up emoticon |
      |Emoji              |
      |More               |
      |More info          |
      |like to know more  |
      |ok                 |
      |Need help          |