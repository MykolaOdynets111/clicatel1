@no_widget
@dot_control
Feature: Supervisor desk

  Background:
    Given Create .Control integration for Automation and adapter: fbmsg
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned

  Scenario: Supervisor desk: Verify if supervisor can see agents profile picture in conversation area
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Settings in left menu and User Management in submenu
    And Click 'Manage' button for Taras AQA user
    And Click 'Upload' button
    When Upload new photo
    Then New image is saved on portal and backend
    When Admin click BACK button in left menu
    And Send message call
    Given Agent launch agent desk from portal
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    And Agent responds with welcome to User
    When Agent switches to opened Portal page
    And I select Touch in left menu and Supervisor Desk in submenu
    And Verify Agent Icon is shown on Supervisor Desk Chat View
