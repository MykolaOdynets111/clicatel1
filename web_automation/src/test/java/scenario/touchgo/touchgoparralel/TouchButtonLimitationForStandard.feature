Feature: User of Standard tenant should not see tbutton

  Scenario: tbutton should be turned off for Standard
    Given User select Standard AQA tenant
    And Click chat icon
    Then Touch button is not shown
