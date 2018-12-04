@portal
@widget_disabling
@no_chatdesk
Feature: Disabling Widget

  Background:
    Given Widget is enabled for Starter AQA tenant

  Scenario: Agent should be able to disable widget from admin
    Given I open portal
    And Login into portal as an admin of Starter AQA account
    When I select Touch in left menu and Configure Touch in submenu
    And Disable the web chat
    Then Status of Web Chat integration is changed to "Not Active"
    When User select Starter AQA tenant
    Then Chat icon is not visible