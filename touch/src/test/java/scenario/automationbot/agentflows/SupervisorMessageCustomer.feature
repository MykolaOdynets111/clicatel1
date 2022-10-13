@support_hours
@no_widget
@dot_control
@start_server
Feature: Supervisor desk



  @TestCaseId("https://jira.clickatell.com/browse/TPORT-7404")
    @skip
  Scenario: Supervisor desk:: verify if Supervisor is able to send message to customer via message customer option
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    When Agent select dotcontrol ticket
    And Click on Message Customer button
    Then Message Customer Window is opened
    When Supervisor send hello to agent trough adapter:fbmsg chanel
    Then Verify dot .Control returns hello response during 10 seconds