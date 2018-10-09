package portal_pages;

import portal_pages.uielements.LeftMenu;

public class PortalMainPage extends PortalAbstractPage {

    private LeftMenu leftMenu;

    public LeftMenu getLeftMenu() {
        waitForElementToBeVisible(leftMenu.getWrappedElement());
        return leftMenu;
    }
}
