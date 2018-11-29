#@tie
#Feature: Tie SAVINGS entity recognition
#
#  Scenario Outline: Tie should recognize SAVINGS entity from a "<message>" message
#    Then If I send a "<message>" to generalbank tenant TIE should return "SAVINGS" entity
#
#    Examples:
#    | message                                                      |
#    |Do you offer savings accounts?                                |
#    |I want to open a savings account, please.                     |
#    |Tell me about savings account options, please.                |
#    |Do you have savings or just checking?                         |
#    |I want more info on savings account, please.                  |
#    |What is the interest on your savings account, please?         |
#    |Are there any fees on your savings account, please?           |
#    |I want to learn more about savings products, please.          |
#    |I want to understand your savings offerings, please.          |
#    |Tell me more about savings products, please.                  |
#    |Do you have some info about savings products?                 |
#    |Do you have some info about savings offerings?                |
#    |What about savings accounts?                                  |