package steps;

import api_helper.ApiHelper;
import cucumber.api.java.en.Given;
import dataprovider.Tenants;
import driverManager.ConfigManager;
import driverManager.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
            LocalDateTime currentTimeIntenantTimeZone = getCurrentTimeInTenantTimeZone(tenantOrgName);
            LocalDateTime startTime =currentTimeIntenantTimeZone.minusHours(3);
            LocalDateTime endTime = currentTimeIntenantTimeZone.plusHours(3);

            ApiHelper.setWidgetVisibilityDaysAndHours(tenantOrgName, currentTimeIntenantTimeZone.toString(),
                    startTime.format(formatter),
                    endTime.format(formatter));
        }
        if (day.equalsIgnoreCase("wrong hours")) {
            LocalDateTime currentTimeIntenantTimeZone = getCurrentTimeInTenantTimeZone(tenantOrgName);

            LocalDateTime startTime =currentTimeIntenantTimeZone.minusHours(6);
            LocalDateTime endTime = currentTimeIntenantTimeZone.minusHours(5);

            ApiHelper.setWidgetVisibilityDaysAndHours(tenantOrgName, currentTimeIntenantTimeZone.getDayOfWeek().toString(),
                    startTime.format(formatter),
                    endTime.format(formatter));
        }
    }

    private LocalDateTime getCurrentTimeInTenantTimeZone(String tenantOrgName){
        String tenantTimeZone = ApiHelper.getTenantConfig(Tenants.getTenantInfo(tenantOrgName, "id"), "timezone");
        String zoneOffset = tenantTimeZone.split(":")[0].replace("GMT", "");
        ZoneId.of(zoneOffset);
        return LocalDateTime.now(ZoneId.of(zoneOffset));
    }

    @Given("^(.*) territory availability is applied$")
    public void setUpWidgetVisibilityByTerritory(List<String> territoryConfig){
        List<String> territory = getCorrectTerritory(territoryConfig);
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

    private List<String> getCorrectTerritory(List<String> territories){
        List<String> territory = new ArrayList<>();
        if (territories.get(0).equalsIgnoreCase("My territory")&ConfigManager.isRemote()){
            territory.add("North America");
            territory.add("United States");
        }
        if (territories.get(0).equalsIgnoreCase("My territory")&!(ConfigManager.isRemote())){
            territory.add("Europe");
            territory.add("Ukraine");
        }else {
            return territories;
        }
        return territory;
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
