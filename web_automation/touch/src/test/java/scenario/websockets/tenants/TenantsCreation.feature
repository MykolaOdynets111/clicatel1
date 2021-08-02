Feature: Precondition on Agents creation

  Scenario Outline: Create Tenants and Agents
    When <tenant> tenant is created
    And Main agent with <firstAgentName> name is created
    And Second agent with <secondAgentName> name is created

    Examples:
      | tenant            | firstAgentName   | secondAgentName    |
#      | General Bank Demo | GBD Main         | GBD Second         |
#      | Starter AQA       | SAQA Main        | SAQA Second        |
#      | Automation        | Auto Main        | Auto Second        |
#      | Automation Bot    | Autobot Main     | Autobot Second     |
      | Standard Billing  | SB Main          | SB Second          |
#      | Attachments       | Attachments Main | Attachments Second |