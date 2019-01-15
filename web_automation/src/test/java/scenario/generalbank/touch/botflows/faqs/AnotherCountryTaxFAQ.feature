Feature: General Bank FAQ: "What are my tax obligations if I pay tax in another country?" intent

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response for his '<user input>' input
    Examples:
      | user input                                                        |expected response|
      |What are my tax obligations if I pay tax in another country? |Hi ${firstName}. If you are liable to pay tax in a country other than South Africa we need your foreign tax number. General bank is required by law to obtain certain international tax information from all our clients. The information will be reported to the relevant countries through the South African Revenue Services (SARS), based on the United States Foreign Account Tax Compliance Act (FATCA) and the Organisation for Economic Co-operation and Development (OECD) Common Reporting Standard for the Automatic Exchange of Financial Account Information. New and existing clients are required to confirm all countries other than South Africa where they are a resident for tax purposes or liable to pay tax. Clients must provide their tax identification number for the listed countries. If you are unsure of any information, please contact a professional tax consultant.|