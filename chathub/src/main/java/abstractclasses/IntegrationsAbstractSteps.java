package abstractclasses;

import chathubpages.IntegrationsPage;
import interfaces.JSHelper;
import interfaces.VerificationHelper;
import interfaces.WebWait;


public class IntegrationsAbstractSteps implements JSHelper, VerificationHelper, WebWait {

    private static final ThreadLocal<IntegrationsPage> currentIntegrationsPage = new ThreadLocal<>();

    public static IntegrationsPage getIntegrationsPage() {
        if (currentIntegrationsPage.get() == null) {
            currentIntegrationsPage.set(new IntegrationsPage());
        }
        return currentIntegrationsPage.get();
    }


}
