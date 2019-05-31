package portalpages.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class AgentRowChatConsole extends Widget implements WebActions {
    private WebElement baseWebElem = this.getWrappedElement();

    @FindBy(css = "tr.animate-repeat.ng-scope td[cl-mobile-title='Agent']")
    private WebElement agentName;

    public AgentRowChatConsole(WebElement element) {
        super(element);
    }

    public String getAgentName(){
        return agentName.getText();
   }


}
