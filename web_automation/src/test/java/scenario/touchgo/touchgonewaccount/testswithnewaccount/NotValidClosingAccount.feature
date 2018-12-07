@portal
@no_widget
@no_chatdesk
@signup_account
Feature: Closing account should require verification

  Scenario: Admin of the account should be able to cancel closing the account
    Given I open portal
    And Login into portal as an admin of SignedUp AQA account
    When I click Launchpad button
    When I select Settings in left menu and Account details in submenu
    And Click 'Close account' button
    Then 'Close account?' popup is shown
    When Admin clicks 'Cancel' button in confirmation popup
    Then Account details page is opened

  Scenario: Admin of the account should be able to cancel closing the account on the Account confirmation step
    Given I open portal
    And Login into portal as an admin of SignedUp AQA account
    When I click Launchpad button
    When I select Settings in left menu and Account details in submenu
    And Click 'Close account' button
    Then 'Close account?' popup is shown
    When Admin clicks 'Close account' button in confirmation popup
    Then 'Account confirmation' popup is shown
    When Admin clicks 'Cancel' button in confirmation popup
    Then Account details page is opened


  Scenario: Admin of the account should not be able to close the account with incorrect confirmation
    Given I open portal
    And Login into portal as an admin of SignedUp AQA account
    When I click Launchpad button
    When I select Settings in left menu and Account details in submenu
    And Click 'Close account' button
    Then 'Close account?' popup is shown
    When Admin clicks 'Close account' button in confirmation popup
    Then 'Account confirmation' popup is shown
    When Admin provides 'invalid_email@aqa.test' email and 'p@$$w0rd4te$t' pass for account confirmation
    When Admin clicks 'Close account' button in confirmation popup
    Then Error about invalid credentials is shown
    And Account details page is opened


  Scenario Outline: Admin of the account should not be able to close the account with incorrect confirmation: <email>, <pass>
    Given I open portal
    And Login into portal as an admin of SignedUp AQA account
    When I click Launchpad button
    When I select Settings in left menu and Account details in submenu
    And Click 'Close account' button
    Then 'Close account?' popup is shown
    When Admin clicks 'Close account' button in confirmation popup
    Then 'Account confirmation' popup is shown
    When Admin provides '<email>' email and '<pass>' pass for account confirmation
    When Admin clicks 'Close account' button in confirmation popup
    Then 'Account confirmation' popup is shown
    Examples:
      |email                    |pass         |
      |                         |             |
      |                         |p@$$w0rd4te$t|
      |account_signup@aqa.test  |             |