@tie
Feature: VM: TIE sentiments and 2 intents for user choice card

  Scenario Outline: Verify if TIE sentiment is NEUTRAL or POSITIVE and 2 intents are returned for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for Virgin Money tenant
    And TIE returns 2 intents: "claims and premiums auto or household, policy queries auto or household" on '<user_message>' for Virgin Money tenant
    Examples:
      |user_message  |
      |Car insurance |
      |Car           |
      |auto          |
      |household     |
      |home          |

  Scenario Outline: Verify if TIE sentiment is NEUTRAL or POSITIVE and 2 intents are returned for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for Virgin Money tenant
    And TIE returns 2 intents: "claims and premiums funeral, policy queries funeral" on '<user_message>' for Virgin Money tenant
    Examples:
      |user_message      |
      |Funeral insurance |
      |Funeral           |
