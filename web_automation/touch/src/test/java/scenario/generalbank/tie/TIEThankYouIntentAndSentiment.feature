@tie
Feature: TIE should correctly identify "thank you" intent

  Scenario Outline: Verify if TIE correctly identifies "thank you" intent from following message: "<user_message>"
    Then TIE response should have correct top intent: "thank you" on '<user_message>' for General Bank Demo tenant
    And TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for General Bank Demo tenant

    Examples:
      |user_message                                  |
      |Thank you                                     |
      |Thank you!                                    |
      |thanks                                        |
      |thks                                          |
      |tx                                            |
      |thx                                           |
      |many thanks                                   |
      |thanks so much                                |
      |thanks for your help                          |
      |thank you so much for your help               |
      |a big thank you                               |
      |OK, Thanks                                    |