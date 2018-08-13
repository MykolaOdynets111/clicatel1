@tie
Feature: TIE should give correct sentiments and intent for policy queries questions

  Scenario Outline: Verify if TIE sentiment is NEUTRAL or POSITIVE for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for Virgin Money tenant
    And TIE returns 1 intent: "policy queries" on '<user_message>' for Virgin Money tenant

    Examples:
      |user_message                             |
      |Age limits of policy holder & dependants |
      |How old must I be to apply for a policy? |
      |Whats the cut off age for a policy?      |