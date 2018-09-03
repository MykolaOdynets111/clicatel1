@tie
Feature: VM: TIE sentiments and intent regarding generic intent

  Scenario Outline: Verify if TIE sentiment is NEUTRAL or POSITIVE for the following message: "<user_message>"
    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for Virgin Money tenant
    And TIE returns 1 intent: "loans" on '<user_message>' for Virgin Money tenant

    Examples:
      |user_message       |
      |Loan queries        |
      |Easy Money          |
      |Loan                |
      |Do you offer loans  |
      |Personal loan       |
      |Borrow money        |
      |Need money          |