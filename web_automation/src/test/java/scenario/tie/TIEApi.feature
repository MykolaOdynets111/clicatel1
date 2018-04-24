@tie
Feature: Testing TIE APIs

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

  Scenario: Intent endpoint for specific intent should work
            API: GET /tenants/<tenant_name>/intents/<intent_text>
    When I send "check balance" intent for generalbank tenant then response code is 200 and intents are not empty

  #  After  TPLAT-2648 is fixed:
  # ToDo: Add test on GET /tenants/<tenant_name>/intents/<intent>/train/<sample_text>

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

  ### TIE trainings###

    # ToDo: When is TPLAT-2649 closed Add tests on GET /tenats/<tenant_name>/train and GET /tenats/all/train

  ### Data set and config management ##

  Scenario: User should be able to get and update tenant configs
          API GET /tenants/<tenant_name>/config
          API POST /tenants/<tenant_name>/config
    Given  I create new tenant with TIE API
    And Wait for a minute
    When I make request to see tenant config I receive response with tenant's config
    When I add additional field aqaTest value to the new tenant config
    Then New additional field with aqaTest value to the new tenant config

    # ToDo: Investigate and add test on API POST /tenants/<tenant_name>/trainset/<resource name>


  ### Tenant management ###

  Scenario: User should be able to create and remove new tenant
          API PUT /tenants/ data={'tenant': 'TESTONE'}
          API DELETE /tenants/?tenant=TESTONE
    When I create new tenant with TIE API
    And Wait for a minute
    Then I receives response on my input check balance
    When I delete created tenant
    And Wait for a minute
    Then I am not receiving the response for this tenant on check balance

  Scenario: User should be able to clone tenant
        API PUT /tenants/ data={'tenant': 'TESTONE', 'source_tenant':'generalbank'}
    When I create a clone of generalbank tenant with TIE API
    And Wait for a minute
    Then Config of cloned intent is the same as for generalbank

    # ToDo: Add test on API POST tenants/?tenant=TESTONE&clear=nlp_config,train_data