@portal
@widget_disabling
@no_chatdesk
@touch_go
Feature: Disabling Widget

  Background:
    Given Widget is enabled for Automation tenant

  Scenario: Agent should be able to disable widget from admin
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Configure Touch in submenu
    And Disable the web chat integration
    Then Status of Web Chat integration is changed to "Not Active"
    When User select Automation tenant
    Then Chat icon is not visible