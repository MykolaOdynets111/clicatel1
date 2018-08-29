@tie
Feature: VM: TIE sentiments and intent for Credit card

  Scenario Outline: Verify if TIE sentiment is NEUTRAL or POSITIVE for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for Virgin Money tenant
    And TIE returns 1 intent: "credit card" on '<user_message>' for Virgin Money tenant

    Examples:
      |user_message            |
      |Apply for a credit card |
      |I want a card           |
      |apply for a credit card |

