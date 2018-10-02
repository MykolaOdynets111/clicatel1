#@tie
#Feature: Testing TIE APIs
#
#  ### User input ###
#
#  Scenario: User should be able to get and filter statistic with user inputs
#          API GET /tenants/{tenant}/user_inputs/?{PARAMS}
#    Given  I create new tenant with TIE API
#    And Wait for a minute
#    When I make user statistic request it returns empty response
#    When I send "account balance" for a new tenant then response code is 200 and intents are not empty
#    When I send "111" for a new tenant then response code is 200 and intents are not empty
##    Then User statistic call returns "account balance", "111" user's inputs
#
#
#
