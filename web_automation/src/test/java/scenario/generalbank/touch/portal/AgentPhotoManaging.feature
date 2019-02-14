@portal
@no_widget
@agent_photo
Feature: Managing Agent's photo

  Scenario: Adding agent new photo
    Given Agent of Automation Bot tenant has no photo uploaded
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Manage Agent users in submenu
    And Click 'Manage' button for Taras Aqa user
    And Click 'Upload' button
    When Upload new photo
    Then New image is saved on portal and backend
    When I select Touch in left menu and Launch Chat Desk in submenu
    Then Agent photo is updated on chatdesk

