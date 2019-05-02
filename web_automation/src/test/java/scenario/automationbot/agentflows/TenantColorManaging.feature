@no_widget
Feature: Managing brand color

  Scenario: Check changing second color
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Touch preferences in submenu
    And Click "Configure your brand" nav button
    And Change secondary color to '#ff4ded' for tenant
    And User select Automation Bot tenant
    Then I check secondary color to '#ff4ded' for tenant in widget
    When I login with the same credentials in another browser as an agent of Automation Bot
    Then I check secondary color to '#ff4ded' for tenant in agent desk

  Scenario: Check primary second color
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Touch preferences in submenu
    And Click "Configure your brand" nav button
    And Change primary color to '#ff4dec' for tenant
    And User select Automation Bot tenant
    And I login with the same credentials in another browser as an agent of Automation Bot
    Then I check primary color to '#ff4dec' for tenant in widget
    When User enter connect to agent into widget input field
    Then I check primary color to '#ff4dec' for tenant in agent desk
#    Then check primary color for incoming chat and 360Container ToDo


