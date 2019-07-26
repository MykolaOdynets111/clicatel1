@agent_info
Feature: Agent information should be present in the info popup

  Scenario: Viewing agent's details in information popup
    When I login as agent of General Bank Demo
    Then Icon should contain agent initials of General Bank Demo
    When I click icon with agent initials
    Then I see agent of General Bank Demo info
    When Agent clicks "Profile Settings" button
    Then Agent of General Bank Demo info details is shown in profile window