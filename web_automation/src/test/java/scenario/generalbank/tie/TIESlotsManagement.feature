@slot_management
Feature: TIE slots management

  Scenario: User should be able to create, edit and remove slots
    When I create MONEY type slot for "balance check" intent of Automation Bot tenant
    Then Created slot is saved
    And New slot is returned in TIE response on check balance message
    When I update slot
    And New slot is returned in TIE response on are you open on Monday? message
    When I delete slot
    Then Slot for "balance check" message is not returning anymore