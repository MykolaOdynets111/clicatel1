#@tie
#Feature: Tie WEALTH entity recognition
#
#  Scenario Outline: Tie should recognize WEALTH entity from a "<message>" message
#    Then If I send a "<message>" to generalbank tenant TIE should return "WEALTH" entity
#
#    Examples:
#    | message                                                      |
#    |I like to find out more about your wealth management services.|
#    |Do you have some info on wealth management.                   |
#    |Do you have some info on funds?                               |
#    |What kind of asset management services do you provide?        |
#    |Tell me more about your investment services.                  |
#    |Do you provide asset management services.                     |
#    |Do you offer stocks and bonds services?                       |
#    |Do you offer bonds services?                                  |
#    |Do you offer stocks services?                                 |
#    |What kind of mutual funds do you have?                        |
#    |Mutual funds services, please?                                |
#    |I want to learn about your investment services.               |
#    |Can I invest stocks and bonds with you?                       |
#    |Can I invest stocks with you?                                 |
#    |Can I invest bonds with you?                                  |
#    |What funds do you offer?                                      |
#    |I want more info on investment accounts.                      |
#    |I want to open an investment account with you.                |
#    |Wealth management services, please.                           |