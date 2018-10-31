@portal
@no_widget
@no_chatdesk
Feature: Touch Go plan info

  Scenario: Admin of tenant with Starter plan should see his plan and not see 'Add Agent seats' button
    When I open portal
    And Login into portal as an admin of Starter AQA account
    Then Admin should see "TOUCH GO STARTER" in the page header
    And Not see "Add Agent seats" button
