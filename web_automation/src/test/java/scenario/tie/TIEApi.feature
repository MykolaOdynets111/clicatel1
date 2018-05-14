@tie
Feature: Testing TIE APIs

  Background:
    Given Listener for logging request and response is ready

  ### Chat ###

  Scenario: Chat endpoint for intents only should work
            API: GET /tenants/<tenant_name>/chats/?q=<user input>
    When I send "account balance" for generalbank tenant then response code is 200 and intents are not empty

  Scenario: Chat endpoint for intents & tie_sentiment should work
            API: GET /tenants/<tenant_name>/chats/?q=<user input>&tie_sentiment=True
    When I send "account balance" for generalbank tenant including tie_sentiment then response code is 200 and intents are not empty

  # After issue TPLAT-2647 is fixed:
  # ToDo: add test on POST /tenants/<tenant_name>/chats/?q=<user input> data={'semantic_candidates': ['<text1>', '<text2>']}
  # ToDo: add test on GET /tenants/<tenant_name>/chats/?q=<user input>&usesemantic=True
  # ToDo: add test on GET /tenants/<tenant_name>/chats/?q=<user input>&type=<type name>

  ### Intent ###

  # API: GET /tenants/<tenant_name>/intents/<intent_text>,
  # API: GET /tenants/<tenant_name>/intents/<intent>/train/<sample_text> endpoints are removed and not used

  ### Sentiments ###

  Scenario: Sentiments for use message should work
            API: GET /tenants/<tenant_name>/sentiment/?q=<user input>
    When I send "account Balance" for generalbank tenant then sentiments response code is 200

   ### Intent Answers ###

  Scenario: Tie should be able to give list of answers
            API GET /tenants/{tenant}/answers_map/?intents=<normalized intent name>,<normalized intent name>
    When I send balance check,trading hours for generalbank tenant then response code is 200 and list of answers is shown

  Scenario: Tie should not give answer if there is only one intent
            API GET /tenants/{tenant}/answers_map/?intents=<normalized intent name>,<normalized intent name>
    When I send only balance check for generalbank tenant then response code is 404

  Scenario: Tie should return list of all categories
            API GET /tenants/{tenant}/answers/?category=all
    When I want to get all categories for generalbank response has status 200

  Scenario: Tie should return list of all answers for specific category
            API GET /tenants/{tenant}/answers/?category={CATEGORY ID}
    When I want to get all answers of Some FAQ category for generalbank response has status 200

  ### TIE trainings ###

  Scenario: TIE API about all trainings should work
            API GET /tenants/all/train
    When I want to get trainings for all tenants response status should be 200 and body is not empty

  Scenario: TIE should return training for existed tenant
            API GET /tenants/<tenant_name>/train
    When I want to get trainings for generalbank tenant response status should be 200 and body is not empty

            # ToDo: Extend test when TPLAT-2648 is fixed
  Scenario: User should be able to fill training set with new sample text for selected intent and schedule training for created tenant
            API POST /tenats/<tenant_name>/train
            API GET /tenants/<tenant_name>/train
    Given  I create new tenant with TIE API
    And Wait for a minute
    When I schedule training for a new tenant
    Then Training for new tenant is scheduled
    And All trainings should contain newly added tenant training

  ### Data set and config management ##

  Scenario: User should be able to retrieve all tenants's trainset
          API GET /tenants/<tenant_name>/trainset
    When I make a request to see generalbank trainset I receive response with 200 code and not empty body

     #ToDo: add additional verification when TPLAT-2666 is fixed (that trainset is added)
  Scenario: User should be able to get and update tenant configs and trainset
          API GET /tenants/<tenant_name>/config
          API POST /tenants/<tenant_name>/config
          API POST /tenants/<tenant_name>/trainset/<resource name>
    Given  I create new tenant with TIE API
    And Wait for a minute
    When I make request to see tenant config I receive response with tenant's config
    When I add additional field aqaTest value to the new tenant config
    Then New additional field with aqaTest value is added to tenant config
    When I send test trainset for newly created tenant status code is 200

  ### Tenant management ###

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


  Scenario: User should be able to clone tenant
        API PUT /tenants/ data={'tenant': 'TESTONE', 'source_tenant':'generalbank'}
    When I create a clone of generalbank tenant with TIE API
    And Wait for a minute
    Then Config of cloned intent is the same as for generalbank

  #ToDo: add additional verification when TPLAT-2666 is fixed (that trainset is removed)
  Scenario: User should be able to clear tenant config
        API POST tenants/?tenant=TESTONE&clear=nlp_config,train_data
    Given I create new tenant with TIE API
    And Wait for a minute
    And I add additional field aqaTest value to the new tenant config
    When I clear tenant data
    Then additional field with aqaTest value is removed from tenant config

  ### NER ### under development
#  Scenario: User should be able to add, get and delete NER data set
#          API POST /tenants/ner-trainset
#          API GET /tenants/ner-trainset
#          API DELETE /tenants/ner-trainset/{id of particular data set sample in DB, is displayed in  API GET /tenants/ner-trainse  }
#    When I try to add some trainset response status code should be 200
#    And GET request should return created trainset
