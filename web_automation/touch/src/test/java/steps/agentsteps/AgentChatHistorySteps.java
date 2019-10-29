package steps.agentsteps;

import datamanager.jacksonschemas.chathistory.ChatHistory;

public class AgentChatHistorySteps extends AbstractAgentSteps{

    private void createHistory(){
        ChatHistory history  = new ChatHistory();
    }
}
