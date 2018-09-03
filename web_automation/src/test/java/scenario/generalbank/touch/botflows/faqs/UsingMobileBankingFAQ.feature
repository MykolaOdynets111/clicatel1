#Feature: General Bank FAQ: "How long must I wait until I can use Mobile Banking?" intent
#
#  Background:
#    Given User select General Bank Demo tenant
#    And Click chat icon
#
#  Scenario Outline: Verify if user receives answer on "<user input>" message
#    When User enter <user input> into widget input field
#    Then User have to receive '<expected response>' text response for his '<user input>' input
#    Examples:
#      | user input                                                        |expected response|
#      |How long must I wait until I can use Mobile Banking? |To reset your Mobile Banking PIN, visit your nearest branch with an identification document or Global One card .|