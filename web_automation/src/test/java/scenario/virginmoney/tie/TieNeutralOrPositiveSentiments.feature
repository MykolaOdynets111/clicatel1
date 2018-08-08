@tie
Feature: TIE should give NEUTRAL or POSITIVE sentiments

  Scenario Outline: Verify if TIE sentiment is NEUTRAL or POSITIVE for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for Virgin Money tenant
    And TIE returns 1 intent: "<intent>" on '<user_message>' for Virgin Money tenant

    Examples:
      |user_message                             |intent             |
      |Do premiums go up after I claim          |claims and premiums|
      |What happens to premiums after I claim   |claims and premiums|
      |How much do premiums go up after I claim |claims and premiums|
      |How does a claim change my premium       |claims and premiums|