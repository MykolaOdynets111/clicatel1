@tie
Feature: Testing TIE APIs

  ### Chat ###

  Scenario: Chat endpoint for intents only should work
            API: GET /tenants/<tenant_name>/chats/?q=<user input>
    When I send "account balance" for generalbank tenant then response code is 200 and intents are not empty

  Scenario: Chat endpoint for intents & tie_sentiment should work
            API: GET /tenants/<tenant_name>/chats/?q=<user input>&tie_sentiment=True
    When I send "account balance" for generalbank tenant including tie_sentiment then response code is 200 and intents are not empty

  Scenario: TIE semantic test
            API POST /tenants/<tenant_name>/chats/?q=<user input> data={'semantic_candidates': ['<text1>', '<text2>']}
    Given  I create new tenant with TIE API
    And Wait for a minute
    When I make post request with semantic candidates

  # API: GET /tenants/<tenant_name>/chats/?q=acount balance&tie_sentiment=True is not used according to TPLAT-2647
  # API: GET /tenants/<tenant_name>/chats/?q=<user input>&usesemantic=True is replaced by
  #       API POST /tenants/<tenant_name>/chats/?q=<user input> according to TPLAT-2647

  ### Intent ###

  # API: GET /tenants/<tenant_name>/intents/<intent_text>,
  # API: GET /tenants/<tenant_name>/intents/<intent>/train/<sample_text> endpoints are removed and not used

  ### Sentiments ###

  Scenario: Sentiments for use message should work
            API: GET /tenants/<tenant_name>/sentiment/?q=<user input>
    When I send "account Balance" for generalbank tenant then sentiments response code is 200

  ### Data set and config management ##

  Scenario: User should be able to retrieve all tenants's trainset
          API GET /tenants/<tenant_name>/trainset
    When I make a request to see generalbank trainset I receive response with 200 code and not empty body

  Scenario: User should be able to get and update tenant configs and trainset
          API GET /tenants/<tenant_name>/config
          API POST /tenants/<tenant_name>/config
          API POST /tenants/<tenant_name>/trainset/<resource name>
    Given  I create new tenant with TIE API
    And Wait for a minute
    When I make request to see tenant config I receive response with tenant's config
    When I add add_synonyms field false value to the new tenant config
    Then add_synonyms field with false value is added to tenant config
    And Status code is 404 when I add additional field aqaTest value to the new tenant config
    When I send test trainset for newly created tenant status code is 200
    And Trainset is added for newly created tenant

  ### Type ###

  Scenario: Tie model type tests
          API: GET /tenants/<tenant_name>/chats/?q=<user input>&type=<type name>
    When I make a request with 'I would like to chat to support' user input and 'touch button' type for generalbank tenant then response contains 1 correct intent: connect agent
    When I make a request with 'I would like to chat to support' user input and 'faq' type for generalbank tenant then response contains list of intents and does not contain 'connect agent' intent




