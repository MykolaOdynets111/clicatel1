@portal
@no_widget
@no_chatdesk
@touch_go
Feature: Touch Go plan info

  Scenario: Admin of tenant with Starter plan should see his plan and not see 'Add Agent seats' button
    When I open portal
    And Login into portal as an admin of Automation account
    Then Admin should see "TOUCH GO STARTER" in the page header
    And See "Upgrade" button
    And Not see "Add Agent seats" button
