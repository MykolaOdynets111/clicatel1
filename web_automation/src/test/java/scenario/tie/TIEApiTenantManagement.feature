@tie
Feature: Testing TIE APIs (tenant management)

  ### Tenant management ###

#  change clone from default, not general bank
  Scenario: User should be able to clone tenant
        API PUT /tenants/ data={'tenant': 'TESTONE', 'source_tenant':'generalbank'}
    When I create a clone of generalbank tenant with TIE API
    And Wait for a minute
    Then Config of cloned intent is the same as for generalbank

  Scenario: User should be able to clear tenant config
        API POST tenants/?tenant=TESTONE&clear=nlp_config,train_data
    Given I create new tenant with TIE API
    And Wait for a minute
    When I add add_synonyms field false value to the new tenant config
    And I send test trainset for newly created tenant status code is 200
    When I clear tenant data
    Then add_synonyms field with true value is added to tenant config
    And Added trainset is removed
