package steps.unitysteps;

import abstractclasses.UnityAbstractPage;
import agentpages.AgentLoginPage;
import driverfactory.UnityDriverFactory;
import interfaces.JSHelper;
import interfaces.VerificationHelper;
import interfaces.WebWait;
import productsandservicespages.ProductsAndServicesPage;
import unitypages.UnityLandingPage;
import unitypages.UnityLoginPage;


public class AbstractUnitySteps implements JSHelper, VerificationHelper, WebWait {

    private static final ThreadLocal<UnityLoginPage> currentUnityLoginPage = new ThreadLocal<>();

    private static final ThreadLocal<UnityLandingPage> currentUnityLandingPage = new ThreadLocal<>();
    private static final ThreadLocal<ProductsAndServicesPage> ProductsAndServicesPage = new ThreadLocal<>();

    public static void cleanAllPages() {
        currentUnityLoginPage.remove();
    }

    public static UnityLoginPage getLoginForUnity() {
        if (currentUnityLoginPage.get() == null) {
            currentUnityLoginPage.set(new UnityLoginPage());
        }
        return currentUnityLoginPage.get();
    }

    public static UnityLandingPage getUnityLandingPage() {
        if (currentUnityLandingPage.get() == null) {
            currentUnityLandingPage.set(new UnityLandingPage(UnityDriverFactory.getUnityDriverInstance()));
            return currentUnityLandingPage.get();
        } else {
            return currentUnityLandingPage.get();
        }
    }

    public static ProductsAndServicesPage getProductsAndServicesPage() {
        if (ProductsAndServicesPage.get() == null) {
            ProductsAndServicesPage.set(new ProductsAndServicesPage(UnityDriverFactory.getUnityDriverInstance()));
            return ProductsAndServicesPage.get();
        } else {
            return ProductsAndServicesPage.get();
        }
    }
}