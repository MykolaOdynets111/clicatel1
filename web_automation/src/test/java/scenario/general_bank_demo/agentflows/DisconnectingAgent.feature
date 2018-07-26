Feature: Agent should be disconnected from the first browser if he logs in the second one

  Scenario: Agent should be disconnected from the first browser if he logs in the second one
    Given I login as agent of General Bank Demo
    When I login with the same credentials in another browser as an agent of General Bank Demo
    Then In the first browser Connection Error should be shown