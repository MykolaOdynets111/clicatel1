@no_widget
@dot_control
Feature: Creating .Control integration and sending messages

  @agent_support_hours
  @start_server
  Scenario: Sending init call to .Control out of support hours
    Given Create .Control integration for Automation Bot tenant
    Given Set agent support hours with day shift
    When Send init call with provided messageId correct response without of support hours is returned
    And MessageId is not null

  @agent_session_capacity
  @start_server
  Scenario: Sending init call to .Control with no session capacity
    Given Set session capacity to 0 for Automation Bot tenant
    Given I login as agent of Automation Bot
    Given Create .Control integration for Automation Bot tenant
    When Send init call with provided messageId and no active agents correct response is returned
    And MessageId is not null

  @start_server
  Scenario: Sending init call with not registered apiToken
    Given Create .Control integration for General Bank Demo tenant
    When Send init call with provided messageId and not registered apiToken then correct response is returned

  @start_server
  Scenario: Sending init call with empty clientId
    Given Create .Control integration for General Bank Demo tenant
    When Send init call with provided messageId and empty clientId then correct response is returned
