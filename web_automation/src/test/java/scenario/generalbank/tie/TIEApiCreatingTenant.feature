@tie
Feature: Testing TIE APIs (creating and removing tenant)

  Scenario: User should be able to create and remove new tenant and not be able to create duplicated tenant
            API PUT /tenants/ data={'tenant': 'TESTONE'}
            API DELETE /tenants/?tenant=TESTONE
    When I create new tenant with TIE API
    And Wait for a minute
    Then I receives response on my input check balance
    When I try to create tenant with the same name I should receive 404 response code
    When I delete created tenant
    And Wait for a minute
    Then I am not receiving the response for this tenant on check balance

