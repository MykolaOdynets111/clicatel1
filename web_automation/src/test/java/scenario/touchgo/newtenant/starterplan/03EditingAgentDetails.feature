@no_widget
@no_chatdesk
Feature: Editing agent name

  Scenario: Editing agent name
    Given New tenant is successfully created
    Given Second agent of SignedUp AQA is successfully created
    Given I open portal
    And Login into portal as an admin of SignedUp AQA account
    When I select Touch in left menu and Manage Agent users in submenu
    And Click 'Manage' button for created user
    When Admin updates agent's personal details
    When I select Settings in left menu and User management in submenu
    Then Created user is removed from User management page
    Then Updated user added to User management page





