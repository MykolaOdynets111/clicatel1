#Feature: Creating new intent
#
#  Scenario: Create new intent, train model and apply it
#    When Create new intent for Automation tenant
#    When Adding a few samples for created intent
#    Then Newly added intent is saved
#    When Schedule new training
#    Then New model is ready after 10 secs wait