package steps.unitysteps;

import driverfactory.DriverFactory;
import interfaces.JSHelper;
import interfaces.VerificationHelper;
import interfaces.WebWait;
import myworkspacepages.MyWorkspacePage;
import productsandservicespages.ProductsAndServicesPage;
import unitypages.UnityLandingPage;
import unitypages.UnityLoginPage;


public class AbstractUnitySteps implements JSHelper, VerificationHelper, WebWait {

    private static final ThreadLocal<UnityLoginPage> currentUnityLoginPage = new ThreadLocal<>();
    private static final ThreadLocal<UnityLandingPage> currentUnityLandingPage = new ThreadLocal<>();
    private static final ThreadLocal<ProductsAndServicesPage> ProductsAndServicesPage = new ThreadLocal<>();
    private static final ThreadLocal<MyWorkspacePage> currentMyWorkspacePage = new ThreadLocal<>();



    public static UnityLoginPage getUnityLoginPage() {
        if (currentUnityLoginPage.get() == null) {
            currentUnityLoginPage.set(new UnityLoginPage(DriverFactory.getTouchDriverInstance()));
        }
        return currentUnityLoginPage.get();
    }

    public static UnityLandingPage getUnityLandingPage() {
        if (currentUnityLandingPage.get() == null) {
            currentUnityLandingPage.set(new UnityLandingPage(DriverFactory.getTouchDriverInstance()));
        }
        return currentUnityLandingPage.get();
    }

    public static ProductsAndServicesPage getProductsAndServicesPage() {
        if (ProductsAndServicesPage.get() == null) {
            ProductsAndServicesPage.set(new ProductsAndServicesPage(DriverFactory.getTouchDriverInstance()));
        }
        return ProductsAndServicesPage.get();
    }

    public static MyWorkspacePage getMyWorkspacePage() {
        if (currentMyWorkspacePage.get() == null) {
            currentMyWorkspacePage.set(new MyWorkspacePage(DriverFactory.getTouchDriverInstance()));
        }
        return currentMyWorkspacePage.get();
    }

    public static void cleanAllUnityPages() {

        currentUnityLoginPage.remove();
        currentUnityLandingPage.remove();
        ProductsAndServicesPage.remove();
        currentMyWorkspacePage.remove();
    }
}