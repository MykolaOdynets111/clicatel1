Feature: User of Starter tenant should not see tbutton

  Scenario: tbutton should be turned off for Starter
    Given User select Starter AQA tenant
    And Click chat icon
    Then Touch button is not shown
