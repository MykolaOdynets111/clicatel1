@no_widget
@no_chatdesk
Feature: Editing agent roles

  Scenario: Editing agent roles
    Given New tenant is successfully created
    Given Second agent of SignedUp AQA is successfully created
    Given I open portal
    And Login into portal as an admin of SignedUp AQA account
    When I select Touch in left menu and Manage Agent users in submenu
    And Click 'Manage' button for created user
    When Admin clicks 'Edit user roles'
    And Add new platform admin solution
    When Admin logs out from portal
    And Login as newly created agent
    Then Agent is not redirected to chatdesk
