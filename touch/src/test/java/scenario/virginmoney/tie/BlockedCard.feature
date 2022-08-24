@tie
Feature: VM: TIE sentiments and intent for blocked card questions

  Scenario Outline: Verify if TIE sentiment is NEUTRAL or POSITIVE for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for Virgin Money tenant
    And TIE response on '<user_message>' for Virgin Money tenant contains 'blocked card' intent

    Examples:
      |user_message                                                                               |
      |Why are clients not being notified when their cards have been put on “hold” or “blocked”?  |
      |Why did you block my card                                                                  |
      |Why wasn't I notified when my card was blocked                                             |
      |What notifications do you give for block card                                              |
      |Why is my card on hold?                                                                    |