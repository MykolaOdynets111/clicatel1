@tie
Feature: Testing TIE APIs

  ### User input ###

  #ToDo: cover following test cases for user_inputs API
  # date: start_date, end_date(up to what datetime show records) - done
  # pagination: start, end - blocked by TPLAT-3247
  # text - done
  # sorting: sort=asc, desc
  # confidence: limit max_top_conf=1,  min_top_conf -done

  Scenario: User should be able to get and filter user inputs statistic by date and text
          API GET /tenants/{tenant}/user_inputs/?{PARAMS}
    Given I create new tenant with TIE API
    And Wait for a minute
    When I make user statistic request it returns empty response
    When I send "account balance" for a new tenant then response code is 200 and intents are not empty
    When I send "111" for a new tenant then response code is 200
    When I send "some random message" for a new tenant then response code is 200
    Then User statistic call returns 'account balance, 111, some random message' user's inputs
    When I filter by 'account balance' text only records with appropriate user input text are shown
    When I filter by start_date only records after the date is returned
    When I filter by end_date only records after the date is returned

  Scenario: User should be able to get and filter user inputs statistic by confidence and sort it
      API GET /tenants/{tenant}/user_inputs/?{PARAMS}
    Given I create new tenant with TIE API
    And Wait for a minute
    When I send "trading hours" for a new tenant then response code is 200 and intents are not empty
    When I send "hello" for a new tenant then response code is 200
    When I send "887788" for a new tenant then response code is 200
    Then User statistic call returns 'trading hours, hello, 887788' user's inputs
    When I filter by 'max_top_conf' 0.5 text only records with appropriate user input text are shown
    When I filter by 'min_top_conf' 0.5 text only records with appropriate user input text are shown


