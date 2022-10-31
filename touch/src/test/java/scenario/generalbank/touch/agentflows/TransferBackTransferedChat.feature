@start_orca_server
@orca_api
Feature: Chat transfer back Verification of basic transfer chat functionality

  Background:
    Given I login as agent of General Bank Demo
    When Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2978")
  Scenario: Chat transfer: Agent should be able transfer back transferred chat
    Then Agent has new conversation request
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    Given I login as second agent of General Bank Demo
    And Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    And Second agent can see transferring agent name, orca and following user's message: 'connect to Support'
    When Second agent click "Accept transfer" button
    Then Second agent has new conversation request from orca user
    When Second agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message in it for second agent
    Then Second agent transfers chat
    Then Agent receives incoming transfer with "Incoming Transfer" header
    When Agent click "Accept transfer" button
    Then Agent has new conversation request
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message in it for Agent
    And Agent responds with hello to User
    Then Verify Orca returns hello response during 40 seconds