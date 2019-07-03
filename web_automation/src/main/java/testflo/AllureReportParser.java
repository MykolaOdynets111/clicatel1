package testflo;

import drivermanager.ConfigManager;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonMappingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import testflo.jacksonschemas.AllureScenarioInterface;
import testflo.jacksonschemas.allurescenario.AllureScenario;
import testflo.jacksonschemas.localallurereport.LocalAllureScenario;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AllureReportParser {

    //Should correspond with one stored in build.gradle file
   private static final String ALLURE_REPORT_DATA_FOLDER = System.getProperty("user.dir") + "/build/allure-report/data/";


   private static AllureScenarioInterface parseJSONfILE(File file, ObjectMapper objectMapper){
       if(ConfigManager.isRemoteAllureReport()){
           return parseRemoteAllure(file, objectMapper);
       } else{
           return parseLocalAllure(file, objectMapper);
       }
   }

   private static AllureScenarioInterface parseLocalAllure(File file, ObjectMapper objectMapper){
       try {
           return objectMapper.readValue(file, LocalAllureScenario.class);
       } catch (IOException|NullPointerException e) {
           e.printStackTrace();
           System.out.println("\n !! Was not able to parse Allure test case \n");
       }
       return new AllureScenario();
   }

    private static AllureScenarioInterface parseRemoteAllure(File file, ObjectMapper objectMapper){
       try {
            return objectMapper.readValue(file, AllureScenario.class);
        } catch (IOException|NullPointerException e) {
            e.printStackTrace();
            System.out.println("\n !! Was not able to parse Allure test case \n");
        }
        return new AllureScenario();
    }

   public static List<AllureScenarioInterface> parseAllureResultsToGetTestCases(){
       List<File> allureTestCases;
       File folder;
       if(ConfigManager.isRemoteAllureReport()){
           folder = new File(System.getProperty("user.dir") + "/build/allure-report/data/test-cases");
           allureTestCases= Arrays.asList(folder.listFiles());
       }else{
           folder = new File(ALLURE_REPORT_DATA_FOLDER);
           allureTestCases = Arrays.stream(folder.listFiles())
                   .filter(file -> file.getName().contains("testcase.json"))
                   .collect(Collectors.toList());
       }


       ObjectMapper objectMapper = new ObjectMapper();
       return allureTestCases.stream()
               .map(allureTC -> parseJSONfILE(allureTC, objectMapper))
               .collect(Collectors.toList());
   }

}
