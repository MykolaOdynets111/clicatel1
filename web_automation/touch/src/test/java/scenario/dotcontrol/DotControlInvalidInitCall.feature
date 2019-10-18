@no_widget
@dot_control
Feature: Creating .Control integration and sending messages


  Scenario: Sending init call with not registered apiToken
    Given Create .Control integration for General Bank Demo tenant
    When Send init call with provided messageId and not registered apiToken then correct response is returned

  Scenario: Sending init call with empty clientId
    Given Create .Control integration for General Bank Demo tenant
    When Send init call with provided messageId and empty clientId then correct response is returned
