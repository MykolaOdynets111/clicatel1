@tie
Feature: VM: TIE sentiments and intent for claims questions

  Scenario Outline: Verify if TIE sentiment is NEUTRAL or POSITIVE for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for Virgin Money tenant
    And TIE returns 1 intent: "claims and premiums" on '<user_message>' for Virgin Money tenant

    Examples:
      |user_message                             |
      |Do premiums go up after I claim          |
      |What happens to premiums after I claim   |
      |How much do premiums go up after I claim |
      |How does a claim change my premium       |