@tie
Feature: Testing TIE APIs (Trainings)

  ### TIE trainings ###

  Scenario: TIE API about getting trainings should work (all and for specific tenant)
            API GET /tenants/all/train
            API GET /tenants/<tenant_name>/train
    When I want to get trainings for all tenants response status should be 200 and body is not empty
    When I want to get trainings for existed tenant response status should be 200 and body is not empty

  Scenario: User should be able to fill training set with new sample text for selected intent and schedule training for created tenant
            API POST /tenats/<tenant_name>/train
            API GET /tenants/<tenant_name>/train
    Given  I create new tenant with TIE API
    And Wait for a minute
    When I schedule training for a new tenant
    Then Training for new tenant is scheduled
    And All trainings should contain newly added tenant training



