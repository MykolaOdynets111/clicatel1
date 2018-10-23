@updating_touchgo
Feature: User should be able to upgrade Touch Go PLan

  Scenario: Upgrading Touch Go plan
    When I open portal
    And Login into portal as an admin of Updating AQA account
    When I try to upgrade and buy 3 agent seats
    Then I see "Payment Successful" message