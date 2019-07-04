@start_server
@no_widget
@dot_control
Feature: Creating .Control integration and sending messages for different adapters

  Scenario Outline: Sending message to .Control (to agent) using <adapter> adapter
    Given Create .Control '<adapter>' adapters integration for General Bank Demo tenant
    Given I login as agent of General Bank Demo
    When Send '<message>' messages for .Control '<adapter>' adapter
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    Then Conversation area becomes active with <message> user's message
    Then Valid image for <adapter> integration are shown in left menu with chat
    Then Valid sentiment icon are shown for <message> message in left menu with chat


    Examples:
      |adapter                                             |message|
      |fbmsg                                               |Hate your banking. Hello from fbmsg|
      |whatsapp                                            |Chat to agent from whatsapp        |
      |fbpost                                              |Chat to agent from fbpost          |
      |twdm                                                |Chat to agent from twdm            |
      |twmention                                           |Chat to agent from twmention |
