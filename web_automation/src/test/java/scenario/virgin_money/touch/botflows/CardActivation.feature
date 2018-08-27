Feature: VM flow regarding Card activation

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  Scenario Outline: "Card activation" flow should work for "<user message>" user message
    When User enter <user message> into widget input field
#    Then User have to receive 'Hi ${firstName}. After you've accepted your quote online there are a few more steps before we can activate your card including:\n\n1. Please send relevant documents including your ID, proof of address, 3 months bank statements, 3 months payslips (or an auditors letter if you are self-employed).\n\n2. We will process your request (which can take 7-9 days) and courier your card to you on completion.\n\n3. The final step is for you to call us to setup your pin - detailed instructions will be included in the package couriered to you.\n\nCongrats, and welcome to the family.' text response for his '<user message>' input
    Then User have to receive 'After you've accepted your quote online there are a few more steps before we can activate your card including:\n\n1. Please send relevant documents including your ID, proof of address, 3 months bank statements, 3 months payslips (or an auditors letter if you are self-employed).\n\n2. We will process your request (which can take 7-9 days) and courier your card to you on completion.\n\n3. The final step is for you to call us to setup your pin - detailed instructions will be included in the package couriered to you.\n\nCongrats, and welcome to the family.' text response for his '<user message>' input
    Examples:
      |user message                             |
      |How do I activate my card                |
      |What is the card activation process      |
      |What must I do to activate my card       |
      |What do you need to activate my card     |
      |What documents do I need to fica my card |