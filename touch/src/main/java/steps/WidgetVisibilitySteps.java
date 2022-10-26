package steps;

import apihelper.ApiHelper;
import datamanager.Tenants;
import drivermanager.ConfigManager;
import io.cucumber.java.en.Given;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WidgetVisibilitySteps {

//    @Given("^Following widget time availability for (.*) is selected: (.*)$")
//    public void setUpWidgetVisibilityByTime(String tenantOrgName, List<String> dayTimeVisibility) {
//        Tenants.setTenantUnderTestNames(tenantOrgName);
//        String day = dayTimeVisibility.get(0);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:ss");
//        if (day.equalsIgnoreCase("all week")) {
//            ApiHelper.setWidgetVisibilityDaysAndHours(tenantOrgName, "all week", "00:00", "23:59");
//            return;
//        }
//        if (dayTimeVisibility.size() > 1){
//            List<String> startEndTime = Arrays.asList(dayTimeVisibility.get(1).replace("from", "").trim().split(" to "));
//            ApiHelper.setWidgetVisibilityDaysAndHours(tenantOrgName, day, startEndTime.get(0), startEndTime.get(1));
//        }
//        if (day.equalsIgnoreCase("this day")) {
//            LocalDateTime currentTimeIntenantTimeZone = getCurrentTimeInTenantTimeZone(tenantOrgName);
//            LocalDateTime startTime =currentTimeIntenantTimeZone.minusHours(3);
//            LocalDateTime endTime = currentTimeIntenantTimeZone.plusHours(3);
//
//            ApiHelper.setWidgetVisibilityDaysAndHours(tenantOrgName, currentTimeIntenantTimeZone.toString(),
//                    startTime.format(formatter),
//                    endTime.format(formatter));
//        }
//        if (day.equalsIgnoreCase("wrong hours")) {
//            LocalDateTime currentTimeIntenantTimeZone = getCurrentTimeInTenantTimeZone(tenantOrgName);
//
//            LocalDateTime startTime =currentTimeIntenantTimeZone.minusHours(6);
//            LocalDateTime endTime = currentTimeIntenantTimeZone.minusHours(5);
//
//            ApiHelper.setWidgetVisibilityDaysAndHours(tenantOrgName, currentTimeIntenantTimeZone.getDayOfWeek().toString(),
//                    startTime.format(formatter),
//                    endTime.format(formatter));
//        }
//    }

    private LocalDateTime getCurrentTimeInTenantTimeZone(String tenantOrgName){
        return LocalDateTime.now(Tenants.getTenantZoneId(tenantOrgName));
    }

    @Given("^(.*) territory availability is applied for (.*)$")
    public void setUpWidgetVisibilityByTerritory(List<String> territoryConfig, String tenant){
        Tenants.setTenantUnderTestNames(tenant);
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
        if (territories.get(0).equalsIgnoreCase("My territory")& ConfigManager.isRemote()){
            territory.add(0,"North America");
            territory.add(1,"United States");
        } else {
            if (territories.get(0).equalsIgnoreCase("My territory") & !(ConfigManager.isRemote())) {
                territory.add(0,"Europe");
                territory.add(1,"Ukraine");
            } else {
                return territories;
            }
        }
        return territory;
    }

    @Given("^Widget is turned off for my country only for (.*)$")
    public void turnOffWidgetForCountry(String tenant){
        Tenants.setTenantUnderTestNames(tenant);
        String territory, country;
        if(ConfigManager.isRemote()){
            territory="North America";
            country="United States";
        } else{
            territory="Europe";
            country="Ukraine";
        }
        ApiHelper.setAvailabilityForTerritoryAndCountry(Tenants.getTenantUnderTestOrgName(), territory, true,
                country, false);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Given("^Widget is disabled for (.*) territory but is enabled for (.*) User's country for (.*)$")
    public void turnOffWidgetForTerritory(String territory, String country, String tenant){
        Tenants.setTenantUnderTestNames(tenant);
        if(ConfigManager.isRemote()){
            territory="North America";
            country="United States";
        }
        ApiHelper.setAvailabilityForTerritoryAndCountry(Tenants.getTenantUnderTestOrgName(), territory, false,
                country, true);
    }
}
