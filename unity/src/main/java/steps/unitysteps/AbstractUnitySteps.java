package steps.unitysteps;

import abstractclasses.UnityAbstractPage;
import agentpages.AgentLoginPage;
import interfaces.JSHelper;
import interfaces.VerificationHelper;
import interfaces.WebWait;
import unitypages.UnityLoginPage;


public class AbstractUnitySteps implements JSHelper, VerificationHelper, WebWait {

    private static final ThreadLocal<UnityLoginPage> currentUnityLoginPage = new ThreadLocal<>();

    public static void cleanAllPages(){
        currentUnityLoginPage.remove();
    }

    public static UnityLoginPage getLoginForChatHub() {
        if (currentUnityLoginPage.get()==null) {
            currentUnityLoginPage.set(new UnityLoginPage());
        }
        return currentUnityLoginPage.get();
    }
}