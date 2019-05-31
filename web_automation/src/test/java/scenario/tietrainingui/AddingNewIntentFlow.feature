@tie
@creating_intent
@no_widget
@no_chatdesk
Feature: Creating new intent

  Scenario: Create new intent, train model and apply it
    When Create new intent for Automation Common tenant
    Then New intent is created
    When Adding a few samples for created intent
    When Schedule new training
    Then New model is ready after 16 minutes wait
    When I publish new model
    Then Tie returns new intent