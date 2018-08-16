Feature: Agent information should be present in the info popup

  Scenario: Viewing agent's details in information popup
    When I login as agent of General Bank Demo
    Then Icon should contain General Bank Demo agent's initials
    When I click icon with initials
    Then I see General Bank Demo agent's info
    When I click "Profile Settings" button
    Then General Bank Demo Agent's info derails is shown in profile window