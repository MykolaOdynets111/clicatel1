@portal
@no_widget
@no_chatdesk
@touch_go
Feature: Touch Go plan info

  Scenario: Admin of tenant with Standard plan should see his plan and not see 'Upgrade' button
    When I open portal
    And Login into portal as an admin of Standard Billing account
    Then Admin should see "TOUCH GO STANDARD" in the page header
    And Not see "Upgrade" button
    And See "Add Agent seats" button
