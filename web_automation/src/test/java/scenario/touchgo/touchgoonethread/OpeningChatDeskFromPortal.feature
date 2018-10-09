Feature: Opening chat desk from portal

  Scenario: Admin should be able to open chat desk from portal
    When I open portal
    And Login into portal as an admin of Starter AQA account
    When I select Touch in left menu and Launch Chat Desk in submenu
    Then Agent is logged in chat desk