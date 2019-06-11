Feature: Chat console: Agents tab

  Scenario: Info about agent on Agent tab
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Chat console in submenu
    And Select 'Agents' in nav menu
    Then 'No agents online' on Agents tab shown if there is no online agent
    Given I login as second agent of General Bank Demo
    Then Logged in agents shown in Agents chat console tab
    Given User select General Bank Demo tenant
    And Click chat icon
    When User enter connect to agent into widget input field
    Then Second Agent has new conversation request
    And Second agent is marked with a green dot in chat console
    When Admin clicks expand dot for Second agent
    Then All chats info are shown for second agent including intent on user message connect to agent
    And Correct number of active chats shown for Second agent

  @no_widget
  Scenario: Agent without active chat is marked with yellow dot
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Chat console in submenu
    And Select 'Agents' in nav menu
    Then 'No agents online' on Agents tab shown if there is no online agent
    Given I login as second agent of General Bank Demo
    Given Second agent has no active chats
    Then Logged in agents shown in Agents chat console tab
    And Second agent is marked with a yellow dot in chat console


