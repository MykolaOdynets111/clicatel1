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
    When I add additional field aqaTest value to the new tenant config
    Then New additional field with aqaTest value is added to tenant config
    When I send test trainset for newly created tenant status code is 200
    And Trainset is added for newly created tenant




