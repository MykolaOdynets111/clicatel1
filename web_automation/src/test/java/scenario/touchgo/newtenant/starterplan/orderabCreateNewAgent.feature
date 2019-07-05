@portal
@no_widget
@no_chatdesk
@without_tct
Feature: Creating new agent


  Scenario: Creating new agent
    Given New tenant is successfully created
    Given I open portal
    And Login into portal as an admin of Updating AQA account
    When I select Touch in left menu and Manage Agent users in submenu
    When I click "Add Agent users" page action button
    And Create new Agent
    Then Confirmation Email arrives
    When Second agent opens confirmation URL
    Then Login screen with new Second Agent name opened
    When Second Agent provides new password and click Login
    Then Notification popup with text Your account has been created is shown
    When Login as newly created agent
    Then Agent is logged in chat desk
