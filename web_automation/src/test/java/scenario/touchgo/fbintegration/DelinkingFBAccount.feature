@dilinking_account
Feature: Creating integration with facebook

  Scenario: Admin of the Starter tenant should be able to delink fb integration
    Given I open portal
    And Login into portal as an admin of Starter AQA account
    When I select Touch in left menu and Configure Touch in submenu
    Then Status of Facebook posts & Messenger integration is "Active" in integration card
    When Click 'Manage Facebook' button for Facebook posts & Messenger integration
    And Delink facebook account
    Then Status of Facebook posts & Messenger integration is "Not setup" in integration card
    And Facebook integration is deleted

  Scenario: Admin of the Standard tenant should be able to delink fb integration
    Given I open portal
    And Login into portal as an admin of Standard AQA account
    When I select Touch in left menu and Configure Touch in submenu
    When Click 'Manage Facebook' button for Facebook posts & Messenger integration
    And Delink facebook account
    Then Status of Facebook posts & Messenger integration is "Not setup" in integration card
    And Facebook integration is deleted