@portal
Feature: Admin of tenant with Starter plan should see his plan and not see 'Add Agent seats' button

  Scenario: Agent assist and Feedback should be turned off for Starter
    When I open portal
    And Login into portal as an admin of Starter AQA account
    Then Admin should see "TOUCH GO STARTER" in the page header
    And Not see "Add Agent seats" button
