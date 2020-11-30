Feature:  Dashboard: Agents Performance tab

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4527")
  Scenario: Dashboard:: Info about agent on Active Agent tab
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    And Admin click on Agents Performance dashboard tab
    Then 'No Active agents' on Agents Performance tab shown if there is no online agent
    Given I login as second agent of Standard Billing
    Given User select Standard Billing tenant
    And Click chat icon
    When User enter connect to agent into widget input field
    Then Second Agent has new conversation request
    When Admin clicks expand agents performance table arrow for second agent department
    And Admin see all information about the second agent is filled under active agent tab
    When Admin clicks expand arrow for second agent
    Then Admin see all chats info including intent on user message connect to agent
    And Correct number of active chats shown for Second agent

