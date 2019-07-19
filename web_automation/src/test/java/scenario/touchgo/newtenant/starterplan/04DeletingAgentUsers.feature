@no_widget
@no_chatdesk
Feature: Deleting agent user

  Scenario: Deleting agent
    Given New tenant is successfully created
    Given Second agent of SignedUp AQA is successfully created
    Given I open portal
    And Login into portal as an admin of SignedUp AQA account
    When I select Settings in left menu and User management in submenu
    And Click on created user from the table
    When Admin clicks Delete user button
    Then User is removed from User management page
    Then Newly created agent is deleted on backend
    When Admin logs out from portal
    Then Deleted agent is not able to log in portal

