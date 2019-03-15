@creating_intent
Feature: Creating new intent

  Scenario: Create new intent, train model and apply it
    When Create new intent for Automation Common tenant
    Then New intent is created
    When Adding a few samples for created intent
#    Then Samples are saved
#    When Schedule new training
#    Then New model is ready after 9 minutes wait
#    When I publish new model
#    Then Tie returns new intent