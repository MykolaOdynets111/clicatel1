@no_widget
@dot_control
Feature: Creating .Control integration and sending messages

  @start_server
  Scenario: Sending init call to .Control with active agent (with provided messageId)
    Given Create .Control integration for General Bank Demo tenant
    Given I login as agent of General Bank Demo
    When Send init call with provided messageId correct response is returned
    And MessageId is correctly saved

  @Issue("https://jira.clickatell.com/browse/TPLAT-3627")
  @start_server
  Scenario: Sending init call to .Control with active agent (with auto generated messageId)
    Given Create .Control integration for General Bank Demo tenant
    Given I login as agent of General Bank Demo
    When Send init call with autogenerated messageId correct response is returned
    And MessageId is correctly saved

  @start_server
  Scenario: Sending init call to .Control with active agent (with provided messageId)
    Given Create .Control integration for General Bank Demo tenant
    When Send init call with provided messageId and no active agents correct response is returned
    And MessageId is correctly saved
    Given I login as agent of General Bank Demo
    Then Verify dot .Control returns agents_available response

  @agent_support_hours
  @start_server
  Scenario: Sending init call to .Control out of support hours
    Given Create .Control integration for Automation Bot tenant
    Given Set agent support hours with day shift
    When Send init call with provided messageId correct response without of support hours is returned
    And MessageId is correctly saved

#  @agent_support_hours
#  @start_server
#  Scenario: Sending init call to .Control with no session capasity
#    Given Create .Control integration for Automation Bot tenant
#    Given Set agent support hours with day shift
#    When Send init call with provided messageId correct response without of support hours is returned
#    And MessageId is correctly saved

  @start_server
  Scenario: Sending init call with not registered apiToken
    Given Create .Control integration for General Bank Demo tenant
    When Send init call with provided messageId and not registered apiToken then correct response is returned

  @start_server
  Scenario: Sending init call with empty clientId
    Given Create .Control integration for General Bank Demo tenant
    When Send init call with provided messageId and empty clientId then correct response is returned
