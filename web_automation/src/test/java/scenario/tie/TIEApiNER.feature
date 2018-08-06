@tie
Feature: Testing TIE NER APIs

#  Background:
#    Given Listener for logging request and response is ready

  ### NER ###
  Scenario: User should be able to add, get and delete NER data set
          API POST /tenants/tenant_name/ner-trainset
          API GET /tenants/tenant_name/ner-trainset
          API DELETE /tenants/ner-trainset/{id of particular data set sample in DB, is displayed in  API GET /tenants/ner-trainse  }
    Given  I create new tenant with TIE API
    And Wait for a minute
    When I try to add some trainset response status code should be 200
    And GET request should return created trainset
    When Trying to delete a trainset status code is 200
    And Trainset should be deleted

