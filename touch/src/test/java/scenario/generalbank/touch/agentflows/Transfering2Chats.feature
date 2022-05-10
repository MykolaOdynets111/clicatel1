@dot_control
@multiple_transfer
@no_widget
@test_tr
Feature: Transferring 2 chats

  Verification of more than 1 chat transferring

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-5591")
  Scenario: If Agent gets more than 1 incoming transfer, notification should collapsed
    Given I login as agent of General Bank Demo
    Given First Agent receives a few conversation requests
    Given I login as second agent of General Bank Demo
    When First Agent transfer a few chats
    Then Second Agent receives incoming transfer notification with "Transfer waiting" header and collapsed view
    When Second Agent click on "Transfer waiting" header
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    And Second agent can see transferring agent name, user name from first chat and following user's message: 'connect to agent'


  @agent_session_capacity
  Scenario: Transfer :: Agent with max available chats is not displayed in transfer pop-up
    Given I login as agent of General Bank Demo
    Given Set session capacity to 2 for General Bank Demo tenant
    And First Agent receives a few conversation requests
    And I login as second agent of General Bank Demo
    And Second agent receives one new conversation requests
    When Second agent click on new conversation request from dotcontrol
    When Second agent click on 'Transfer' chat
    Then Transfer chat pop up appears for Second agent
    When Second agent open 'Transfer to' drop down
    Then Second agent should not see first agent in a transfer pop-up agents dropdown