Feature:  Dashboard: Agents Performance tab

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4527")
  Scenario: Dashboard:: Info about agent on Active Agent tab
    #Given User select Standard Billing tenant
    Given Setup ORCA abc integration for Standard Billing tenant
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    And Admin click on Agents Performance dashboard tab
#    Given I login as second agent of Standard Billing
#    And Setup ORCA whatsapp integration for Standard Billing tenant
#    When Send connect to support message by ORCA
#    Then Second Agent has new conversation request
    When Send to agent message by ORCA
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    And I select Touch in left menu and Supervisor Desk in submenu
    When Agent select "Tickets" left menu option
    And Agent search chat orca on Supervisor desk
    When Admin clicks expand agents performance table arrow for second agent department
    And Admin see all information about the second agent is filled under active agent tab
    When Admin clicks expand arrow for second agent
    Then Admin see all chats info including intent on user message connect to agent
    And Correct number of active chats shown for Second agent

