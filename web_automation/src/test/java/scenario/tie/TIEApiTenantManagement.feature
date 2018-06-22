@tie
Feature: Testing TIE APIs (tenant management)

  Background:
    Given Listener for logging request and response is ready

  ### Tenant management ###

  Scenario: User should be able to clone tenant
  API PUT /tenants/ data={'tenant': 'TESTONE', 'source_tenant':'generalbank'}
    When I create a clone of generalbank tenant with TIE API
    And Wait for a minute
    Then Config of cloned intent is the same as for generalbank

  Scenario: User should be able to clear tenant config
  API POST tenants/?tenant=TESTONE&clear=nlp_config,train_data
    Given I create new tenant with TIE API
    And Wait for a minute
    When I add additional field aqaTest value to the new tenant config
    And I send test trainset for newly created tenant status code is 200
    When I clear tenant data
    Then additional field with aqaTest value is removed from tenant config
    And Added trainset is removed
