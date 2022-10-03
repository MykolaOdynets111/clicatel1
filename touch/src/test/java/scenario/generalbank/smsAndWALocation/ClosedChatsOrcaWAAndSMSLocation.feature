@no_widget
@orca_api
@Regression
Feature: Whatsapp ORCA :: ChatDesk

  @start_orca_server
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1829")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1175")
  Scenario Outline: CD :: <channelType> :: Location:: Verify if location shared by agent is displayed on history tab
    Given I login as agent of General Bank Demo
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from <userType> user
    When Agent click on new conversation request from <userType>
    Then Conversation area becomes active with connect to agent user's message
    When Agent sends Lviv Location to User
    Then Verify Orca returns Lviv Location sent by Agent during 40 seconds
    When Agent closes chat
    And Agent select "Closed" left menu option
    And Agent searches and selects chat from <userType> in chat history list
    Then Agent sees correct Location Url in closed chat body
    And Agent sees Lviv location in history preview
    When Agent open first 'History view'
    Then Agent sees correct location URL in History Details window
    Examples:
      | channelType | userType |
      | Whatsapp    | orca     |
      | SMS         | sms      |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1157")
  Scenario: CD :: Agent Desk :: Closed Chat :: History :: Location :: Verify if location shared by user is  displayed on history tab
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    When User send Lviv location message to agent by ORCA
    When Agent sees Lviv Location from User
    When Agent closes chat
    And Agent select "Closed" left menu option
    And Agent searches and selects chat from orca in chat history list
    Then Agent sees correct Location Url in closed chat body
    And Agent sees Lviv location in history preview
    When Agent open first 'History view'
    Then Agent sees correct location URL in History Details window