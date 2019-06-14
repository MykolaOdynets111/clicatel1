Feature: Chat console: Overview tab

  Scenario: Live chats counter
    Given User select General Bank Demo tenant
    And Click chat icon
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Chat console in submenu
    And Save Customer engaging with an Agent pre-test widget value
    Given I login as second agent of General Bank Demo
    And Create fb dm message chat via API
    When User enter connect to agent into widget input field
    Then Second agent has new conversation request
    Then Customer engaging with an Agent counter shows correct live chats number
    And Average chats per Agent is correct

  @no_widget
  Scenario: Total Agents online counter
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Chat console in submenu
    Then Total Agents online widget shows correct number
    Given I login as second agent of General Bank Demo
    Then Total Agents online widget value increased on 1