@portal
@widget_disabling
Feature: Widget disabling

  Scenario: Agent should be able to disable widget from admin
    Given I open portal
    And Login into portal as an admin of Starter AQA account
    When I select Touch in left menu and Configure Touch in submenu
    And Disable the web chat
    Then Status of Web Chat is changed to "Not Active"
    When User select Starter AQA tenant
    Then Chat icon is visible