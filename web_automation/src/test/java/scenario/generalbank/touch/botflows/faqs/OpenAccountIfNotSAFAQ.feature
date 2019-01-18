#Feature: General Bank FAQ: "Can I open an account if I'm not from South Africa?" intent
##  ToDo: Now goes to the agent. Check if this intent still exists
#  Background:
#    Given User select General Bank Demo tenant
#    And Click chat icon
#
#  Scenario Outline: Verify if user receives answer on "<user input>" message
#    When User enter <user input> into widget input field
#    Then User have to receive '<expected response>' text response for his '<user input>' input
#    Examples:
#      | user input                                                            |expected response|
#      |If I am not from South Africa and want to open a General Bank account  | If you're not from South Africa and you want to open a General Bank account , we can help you under any of the following conditions: You have a valid passport You have a valid permanent residence permit You have a temporary work permit Find out more here .|