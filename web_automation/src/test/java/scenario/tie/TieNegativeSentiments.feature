@tie
Feature: TIE should give correct NEGATIVE sentiments

  Scenario Outline: Verify if TIE sentiment is NEGATIVE for the following message: "<user_message>"
    Then TIE sentiment is NEGATIVE when I send '<user_message>' for General Bank Demo tenant

    Examples:
      |user_message                                                   |
      |chat to support                                                |
      |chat support                                                   |
      |Chat to Support                                                |
      |connect to agent                                               |
      |connect agent                                                  |
      |Connect to Agent                                               |
      |Hate your banking                                              |
      |my money was stolen                                            |


