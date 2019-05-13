Feature: Managing brand color

  Scenario: Check changing second color
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Touch preferences in submenu
    And Click "Configure your brand" nav button
    And Change secondary color for tenant
    And User select Automation Bot tenant
    Then I check secondary color for tenant in widget
    When I login with the same credentials in another browser as an agent of Automation Bot
    Then I check secondary color for tenant in agent desk
    Then Return secondary color for tenant

  Scenario: Check changing primary color
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Touch preferences in submenu
    And Click "Configure your brand" nav button
    And Change primary color for tenant
    And User select Automation Bot tenant
    And I login with the same credentials in another browser as an agent of Automation Bot
    Then I check primary color for tenant in widget
    Then I check primary color for tenant in agent desk
    Then Check primary color for incoming chat and 360Container
    Then Return primary color for tenant



