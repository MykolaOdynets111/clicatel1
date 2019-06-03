Feature: Chat console: Agent tab

  Scenario: Info about agent on Agent tab
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Chat console in submenu
    And Select 'Agents' in nav menu
    Then 'No agents online' on Agents tab shown if there is no online agent
    Given I login as second agent of General Bank Demo
    Then Logged in agents shown in Agents chat console tab
#    Given User select General Bank Demo tenant
#    And Click chat icon
#    When User enter chat to support into widget input field
#    Then Agent has new conversation request
#    And Chat console contains info about active chats including intent on user message chat to support
