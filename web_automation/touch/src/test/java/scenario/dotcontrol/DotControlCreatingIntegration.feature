@no_widget
@dot_control
@no_chatdesk
Feature: Creating .Control integration

  Scenario: User can update .Control integration, should receive same apiToken
    When Create .Control integration for General Bank Demo tenant
    When Update .Control integration for General Bank Demo tenant
    When I delete .Control integration
    Then Http integration status is updated after deleting

  Scenario: User can create second .Control integration and should receive new apiToken
    When Create .Control integration for General Bank Demo tenant
    Then Create second .Control integration for General Bank Demo tenant
    When I delete .Control integration
    Then Http integration status is updated after deleting