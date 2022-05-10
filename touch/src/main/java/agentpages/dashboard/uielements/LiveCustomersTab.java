package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".live-customers")
public class LiveCustomersTab extends AbstractUIElement {
    public boolean isLiveCustomersTabOpened() {
        return isElementShown(this.getCurrentDriver(), this.getWrappedElement(), 3);
    }
}
