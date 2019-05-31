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
    When Agent responds with hello from agent to User
#    Then Verify dot .Control returns hello from agent response
#    When Send hi, need your help with my card message for .Control
#    Then Conversation area contains hi, need your help with my card user's message

    Examples:
      |adapter                                             |message   |
      |fbmsg                                               |chat to agent from fbmsg|
      |whatsapp                                            |chat to support from whatsapp|
      |fbpost                                              |chat to support from fbpost|
      |twdm                                                |chat to support from twdm|
      |twmention                                           |chat to agent from twmention|
