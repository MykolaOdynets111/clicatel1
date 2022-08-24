@tie
Feature: TIE should give correct NEGATIVE sentiments

  Scenario Outline: Verify if TIE sentiment is NEGATIVE for the following message: "<user_message>"
    Then TIE sentiment is NEGATIVE when I send '<user_message>' for General Bank Demo tenant

    Examples:
      |user_message                                                   |
#      |you need to get way more ATMs in Cape Town                     |
      |Hate your banking                                              |
      |my money was stolen                                            |
      |Why don't you offer business accounts                          |
      |why can't i view my balance in your app?                       |
#      |What if I don't have proof of residential address?             |

