@no_widget
@orca_api
@Regression
Feature: Agent Date Time Picker Extension

  @TestCaseId("https://jira.clickatell.com/browse/CCD-860")
  @skip
  Scenario Outline: CD :: <channelType> :: Agent Desk :: Live Chat :: Verify the "Date/Time picker" extension cards for the tenant
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    And Agent creates tenant extension with label and name
      | extensionType | label                            | name            |
      | TIME_PICKER   | Schedule Appointment with picker | WaveBank Sranch |
    And I login as agent of General Bank Demo
    When Send chat to agent message by ORCA
    And Agent has new conversation request from <userType> user
    And Agent click on new conversation request from <userType>
    And Conversation area becomes active with chat to agent user's message
    And Agent open c2p form
    And Agent send date picker form with name WaveBank Sranch and send
    And Agent sees extension link with Schedule Appointment with picker name in chat body
    #Can't be finished with Automation as user side validation is pending, after sending extension, UI comes on user side to
    #select option from the extension
    Examples:
      | channelType | userType |
      | ABC         | orca     |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1617")
  @skip
  Scenario Outline: CD :: <channelType> :: Verify that selected emoticons with text (title) display the title of each selected option in same format to agent as they display to user
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    And Agent creates tenant extension with label and name
      | extensionType | label                  | name                       |
      | LIST_PICKER   | MULTI SELECTION OPTION | Travel Accident Protection |
    And I login as agent of General Bank Demo
    When Send chat to agent message by ORCA
    And Agent has new conversation request from <userType> user
    And Agent click on new conversation request from <userType>
    And Conversation area becomes active with chat to agent user's message
    And Agent open c2p form
    And Agent sees extension link with MULTI SELECTION OPTION name in chat body
    Examples:
      | channelType | userType |
      | ABC         | orca     |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1625")
  Scenario Outline: CD :: ABC :: Verify that frequently used extensions will be a sub-set of all extensions list
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    And Agent creates tenant extension with label and name
      | extensionType | label                    | name                         |
      | LIST_PICKER   | MULTI SELECTION OPTION A | Travel Accident Protection A |
      | LIST_PICKER   | MULTI SELECTION OPTION B | Travel Accident Protection B |
    And I login as agent of General Bank Demo
    When Send chat to agent message by ORCA
    And Agent has new conversation request from <userType> user
    And Agent click on new conversation request from <userType>
    And Conversation area becomes active with chat to agent user's message
    And Agent open and select extension with name
      | MULTI SELECTION OPTION A          |
    And Agent sees extension link with MULTI SELECTION OPTION A name in chat body
    Then Agent checks extensions in Frequently Used tab should be available in All Extension tab as well
    Examples:
      | channelType | userType |
      | ABC         | orca     |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1650")
  Scenario: CD :: ABC :: Verify that "Frequently Used" extension tab options according to customer, if used less than 10 extensions, then those will only show up
    Given Setup ORCA ABC integration for General Bank Demo tenant
    And Agent creates tenant extension with label and name
      | extensionType | label                            | name                         |
      | LIST_PICKER   | MULTI SELECTION OPTION A          | Travel Accident Protection   |
      | LIST_PICKER   | MULTI SELECTION OPTION B         | Travel Accident Protection1  |
      | LIST_PICKER   | MULTI SELECTION OPTION C         | Travel Accident Protection2  |
      | LIST_PICKER   | MULTI SELECTION OPTION D         | Travel Accident Protection3  |
      | LIST_PICKER   | MULTI SELECTION OPTION E         | Travel Accident Protection4  |
      | LIST_PICKER   | MULTI SELECTION OPTION F         | Travel Accident Protection5  |
      | LIST_PICKER   | MULTI SELECTION OPTION G         | Travel Accident Protection6  |
      | LIST_PICKER   | MULTI SELECTION OPTION H         | Travel Accident Protection7  |
      | LIST_PICKER   | MULTI SELECTION OPTION I         | Travel Accident Protection8  |
      | LIST_PICKER   | MULTI SELECTION OPTION J         | Travel Accident Protection9  |
      | LIST_PICKER   | MULTI SELECTION OPTION K        | Travel Accident Protection10 |
    And I login as agent of General Bank Demo
    When Send chat to agent message by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Conversation area becomes active with chat to agent user's message
    And Agent open and select extension with name
      | MULTI SELECTION OPTION A          |
      | MULTI SELECTION OPTION B         |
      | MULTI SELECTION OPTION C         |
      | MULTI SELECTION OPTION D         |
      | MULTI SELECTION OPTION E         |
      | MULTI SELECTION OPTION F         |
      | MULTI SELECTION OPTION G         |
      | MULTI SELECTION OPTION H         |
      | MULTI SELECTION OPTION I         |
      | MULTI SELECTION OPTION J         |
      | MULTI SELECTION OPTION K        |
    And Agent checks extensions in Frequently Used tab should be less than 10

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1954")
  Scenario: CD :: Agent Desk :: Pending Chat :: Chat2Pay :: Verify that extension icon is not available for agent in Pending Chat
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And Agent creates tenant extension with label and name
      | extensionType | label              | name        |
      | CHAT_2_PAY    | Send C2P Extension | Chat to Pay |
    And I login as agent of General Bank Demo
    When Send chat to agent message by ORCA
    And Agent has new conversation request
    And Agent click on new conversation request from orca
    And Agent click 'Pending On' chat button
    And Agent select "Pending" left menu option
    And Agent click on new conversation request from orca
    Then Visual indicator with "This chat has been marked as Pending by" text, Agent name and time is shown
    And Agent can't see c2p extension icon