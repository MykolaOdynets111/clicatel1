Feature: Creating integration with facebook

  Scenario: Admin of the Starter tenant should be able to set up fb integration
    Given I open portal
    And Login into portal as an admin of Starter AQA account
    When I select Touch in left menu and Configure Touch in submenu
    When Click 'Configure' button for Facebook posts & Messenger integration
    And Add fb integration with General Bank Integration fb page
    Then Status of Facebook posts & Messenger integration is "Active" in integration card
    And Fb integration is saved on the backend

  Scenario: Admin of the Standard tenant with  should be able to set up fb integration
    Given I open portal
    And Login into portal as an admin of Standard AQA account
    When I select Touch in left menu and Configure Touch in submenu
    When Click 'Configure' button for Facebook posts & Messenger integration
    And Add fb integration with General Bank Standard fb page
    Then Status of Facebook posts & Messenger integration is "Active" in integration card
    And Fb integration is saved on the backend