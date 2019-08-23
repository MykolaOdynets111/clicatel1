@agent_to_user_conversation
@facebook
@fb_dm
@chat_transcript
@without_tct

  @Issue("https://jira.clickatell.com/browse/TPLAT-4385")
  Feature: Feature: Chat Transcript for Facebook
    Scenario: Agent receiving chat transcript after Facebook conversation ends
      Given Set Chat Transcript attribute to ALL for General Bank Demo tenant
      Given Clear Chat Transcript email inbox
      Given I login as agent of General Bank Demo
      Given Open General Bank Demo page
      When User opens Messenger and send message regarding chat to agent
      Then Agent has new conversation request from facebook user
      When Agent click on new conversation request from facebook
      And Save clientID value for facebook user
      Then Conversation area becomes active with chat to agent message from facebook user
      When Agent responds with check chat transcript email to User
      Then User have to receive the following on his message regarding chat to agent: "check chat transcript email"
      Then Agent closes chat
      Then Chat Transcript email arrives
      And Email title contains fbmsg adapter, client ID/Name/Email, chat ID, session number values
      And Email content contains chat history from the terminated conversation