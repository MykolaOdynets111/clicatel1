#Feature: Quote request flow
#
#  Background:
#    Given User select Virgin Money tenant
#    And Click chat icon
#
#  Scenario:  Quote request step1
#    When User enter quote request into widget input field
#    Then Card with a Sure! Please, tell us who we are chatting to? text is shown on user quote request message
#    When User submit card with personal information after user's message: quote request
#    Then Card with a Are you asking about a car, funeral or household insurance policy? text is shown on user <user message> message
#    And Card with a buttons Funeral; Auto or household is shown on user <user message> message
