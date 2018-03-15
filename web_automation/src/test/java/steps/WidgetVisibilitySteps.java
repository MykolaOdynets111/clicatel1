package steps;

import api_helper.ApiHelper;
import cucumber.api.java.en.Given;
import dataprovider.Tenants;
import driverManager.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class WidgetVisibilitySteps {

    @Given("^Following widget time availability for (.*) is selected: (.*)$")
    public void setUpWidgetVisibilityByTime(String tenantOrgName, List<String> dayTimeVisibility) {
        Tenants.setTenantUnderTestOrgName(tenantOrgName);
        String day = dayTimeVisibility.get(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:ss");
        if (day.equalsIgnoreCase("all week")) {
            ApiHelper.setWidgetVisibilityDaysAndHours(tenantOrgName, "all week", "00:00", "23:59");
            return;
        }
        if (dayTimeVisibility.size() > 1){
            List<String> startEndTime = Arrays.asList(dayTimeVisibility.get(1).replace("from", "").trim().split(" to "));
            ApiHelper.setWidgetVisibilityDaysAndHours(tenantOrgName, day, startEndTime.get(0), startEndTime.get(1));
        }
        if (day.equalsIgnoreCase("this day")) {
            LocalDateTime startTime = LocalDateTime.now().minusHours(3);
            LocalDateTime endTime = LocalDateTime.now().plusHours(3);

            LocalDateTime datetime = LocalDateTime.now();
            ApiHelper.setWidgetVisibilityDaysAndHours(tenantOrgName, datetime.getDayOfWeek().toString(),
                    startTime.format(formatter),
                    endTime.format(formatter));
        }
        if (day.equalsIgnoreCase("wrong hours")) {
            LocalDateTime startTime = LocalDateTime.now().minusHours(6);
            LocalDateTime endTime = LocalDateTime.now().minusHours(5);

            ApiHelper.setWidgetVisibilityDaysAndHours(tenantOrgName, LocalDateTime.now().getDayOfWeek().toString(),
                    startTime.format(formatter),
                    endTime.format(formatter));
        }
    }

    @Given("^(.*) territory availability is applied$")
    public void setUpWidgetVisibilityByTerritory(List<String> territory){
        if (territory.get(0).equalsIgnoreCase("all territories")){
            ApiHelper.setAvailableForAllTerritories(Tenants.getTenantUnderTestOrgName());
            return;
        }
        if(territory.size()==1){
            ApiHelper.setAvailabilityForTerritory(Tenants.getTenantUnderTestOrgName(), territory.get(0), true);
            return;
        } else{
            ApiHelper.setAvailabilityForTerritoryAndCountry(Tenants.getTenantUnderTestOrgName(), territory.get(0), true,
                    territory.get(1), true);
        }
    }

    @Given("^Widget is turned off for (.*) country$")
    public void turnOffWidgetForCountry(List<String> territory){

        ApiHelper.setAvailabilityForTerritoryAndCountry(Tenants.getTenantUnderTestOrgName(), territory.get(0), true,
                territory.get(1), false);
    }

    @Given("^Widget is disabled for (.*) territory but is enabled for (.*) User's country$")
    public void turnOffWidgetForTerritory(String territory, String country){
        ApiHelper.setAvailabilityForTerritoryAndCountry(Tenants.getTenantUnderTestOrgName(), territory, false,
                country, true);
    }
}