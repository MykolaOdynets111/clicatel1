@portal
@no_widget
@no_chatdesk
@testing_env_only
Feature: Closing account

  Scenario: Admin of the account should be able to close the account
    Given New account is successfully created
    Given I open portal
    And Login into portal as an admin of SignedUp AQA account
    When I click Launchpad button
    When I select Settings in left menu and Account details in submenu
    And Click 'Close account' button
    Then 'Close account?' popup is shown
    When Admin clicks 'Close account' button in confirmation popup
    Then 'Account confirmation' popup is shown
    When Admin confirms account to close
    And Click 'Close account' button
    Then Admin is not able to login into portal with deleted account
