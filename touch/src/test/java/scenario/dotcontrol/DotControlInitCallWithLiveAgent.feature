@no_widget
@dot_control
Feature: Creating .Control integration and sending messages with live agents

  Scenario: Sending init call to .Control with active agent (with provided messageId)
    Given Create .Control integration for General Bank Demo tenant
    Given I login as agent of General Bank Demo
    When Send init call with provided messageId correct response is returned
    And MessageId is not null

  Scenario: Sending init call to .Control with active agent (with auto generated messageId)
    Given Create .Control integration for General Bank Demo tenant
    Given I login as agent of General Bank Demo
    When Send init call with autogenerated messageId correct response is returned
    And MessageId is not null

  @start_server
  Scenario: Sending init call to .Control with scheduler agent (with provided messageId)
    Given Create .Control integration for General Bank Demo tenant
    When Send init call with provided messageId correct NO_AGENTS_AVAILABLE response is returned
    And MessageId is not null
    Given I login as agent of General Bank Demo
    Then Verify dot .Control returns agents_available response during 40 seconds
