Feature: Managing brand color

  Scenario: Check changing second color
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Touch preferences in submenu
    And Click "Configure your brand" nav button
    And Change secondary color for tenant
    And I launch chatdesk from portal
    Then Agent of Automation Bot is logged in
    And User select Automation Bot tenant
    Then I check secondary color for tenant in widget

  Scenario: Check changing primary color
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Touch preferences in submenu
    And Click "Configure your brand" nav button
    And Change primary color for tenant
    And I launch chatdesk from portal
    Then Agent of Automation Bot is logged in
    When User select Automation Bot tenant
    Then I check primary color for tenant in widget
    And Click chat icon
    Then Welcome message with correct text is shown
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    When User enter hello into widget input field
    Then I check primary color for tenant in opened widget



