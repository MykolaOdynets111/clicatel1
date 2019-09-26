Feature: Managing brand color

  Scenario: Check changing second color
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Touch preferences in submenu
    And Click "Configure your brand" nav button
    And Change secondary color for tenant
    And I launch chatdesk from portal
    And User select Automation Bot tenant
    Then I check secondary color for tenant in widget
    Then I check secondary color for tenant in agent desk

  Scenario: Check changing primary color
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Touch preferences in submenu
    And Click "Configure your brand" nav button
    And Change primary color for tenant
    And I launch chatdesk from portal
    When User select Automation Bot tenant
    Then I check primary color for tenant in widget
    And Click chat icon
    Then Welcome message with correct text is shown
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    Then I check primary color for tenant in opened widget
    Then I check primary color for tenant in agent desk
    Then Check primary color for incoming chat and 360Container



