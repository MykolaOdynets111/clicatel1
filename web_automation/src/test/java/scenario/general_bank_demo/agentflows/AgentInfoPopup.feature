
Feature: Agent information should be present in the info popup

  Scenario: Viewing agent's details in information popup
    When I login as agent of General Bank Demo
    Then Icon should contain agent's initials
