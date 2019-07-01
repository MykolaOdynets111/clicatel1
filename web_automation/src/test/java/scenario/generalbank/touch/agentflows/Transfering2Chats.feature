@start_server
@dot_control
@multiple_transfer
@no_widget
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
    Then Second agent receives incoming transfer with "Incoming transfer" header
    And Second agent can see transferring agent name, user name from first chat and following user's message: 'connect to agent''