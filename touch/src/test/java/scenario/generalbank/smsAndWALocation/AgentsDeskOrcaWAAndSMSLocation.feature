@no_widget
@orca_api
Feature: Whatsapp And SMS ORCA :: ChatDesk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1898")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1174")
  @Regression
  Scenario Outline: CD :: Agent Desk :: Live Chat :: Location :: Verify the end-user can share the current location on WhatsApp via Flow
    Given I login as agent of General Bank Demo
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from <userType> user
    When Agent click on new conversation request from <userType>
    Then Conversation area becomes active with connect to agent user's message
    When User send Lviv location message to agent by ORCA
    When Agent sees Lviv Location from User
    Examples:
      | channelType | userType |
      | whatsapp    | orca     |
      | sms         | sms      |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1831")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1278")
  @Regression
  Scenario Outline: CD :: SMS :: Location:: Verify If agent can't send the location if searched location entered doesn't yield any known result
    Given I login as agent of General Bank Demo
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from <userType> user
    When Agent click on new conversation request from <userType>
    Then Conversation area becomes active with connect to agent user's message
    When Agent open chat location form
    And Agent search for abc abc abc Location
    Then Agent checks Location list size as 0
    And Agent checks Send location for sharing location is not visible
    Examples:
      | channelType | userType |
      | whatsapp    | orca     |
      | sms         | sms      |

  @start_orca_server
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1718")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1964")
  @Regression
  Scenario Outline: CD :: SMS :: Location:: Verify if agent is able to send location to SMS user
    Given I login as agent of General Bank Demo
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from <userType> user
    When Agent click on new conversation request from <userType>
    Then Conversation area becomes active with connect to agent user's message
    And Agent sends Lviv Location to User
    And Verify Orca returns Lviv Location sent by Agent during 40 seconds
    Examples:
      | channelType | userType |
      | whatsapp    | orca     |
      | sms         | sms      |