@dilinking_account
Feature: Creating integration with facebook

  Scenario: Admin should be able to set up fb integration
    Given I open portal
    And Login into portal as an admin of Starter AQA account
    When I select Touch in left menu and Configure Touch in submenu
    When Click 'Manage Facebook' button for Facebook posts & Messenger integration
    And Delink facebook account
    Then Status of Facebook posts & Messenger integration is changed to "Not setup" in integration card
    And Facebook integration is deleted