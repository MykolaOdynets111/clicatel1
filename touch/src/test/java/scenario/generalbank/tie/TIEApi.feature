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

  ### Type ###

  Scenario: Tie model type tests
          API: GET /tenants/<tenant_name>/chats/?q=<user input>&type=<type name>
    When I make a request with 'I would like to chat to support' user input and 'touch button' type for generalbank tenant then response contains 1 correct intent: connect agent
    When I make a request with 'I would like to chat to support' user input and 'faq' type for generalbank tenant then response contains list of intents and does not contain 'connect agent' intent




