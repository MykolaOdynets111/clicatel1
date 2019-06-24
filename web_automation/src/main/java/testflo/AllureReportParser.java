package testflo;

import drivermanager.ConfigManager;
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
       if(ConfigManager.isRemote()){
           return parseRemoteAllure(file, objectMapper);
       } else{
           return parseLocalAllure(file, objectMapper);
       }
   }

   private static AllureScenarioInterface parseLocalAllure(File file, ObjectMapper objectMapper){
       try {
           return objectMapper.readValue(file, LocalAllureScenario.class);
       } catch (IOException e) {
           e.printStackTrace();
       }
       return new AllureScenario();
   }

    private static AllureScenarioInterface parseRemoteAllure(File file, ObjectMapper objectMapper){
        try {
            return objectMapper.readValue(file, AllureScenario.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AllureScenario();
    }

   public static List<AllureScenarioInterface> parseAllureResultsToGetTestCases(){
       List<File> allureTestCases;
       File folder;
//       if(ConfigManager.isRemote()){
//           System.out.println("\n System.getProperty(\"user.dir\")" + System.getProperty("user.dir") + "\n");
//            folder = new File(System.getProperty("user.dir")+"/build/allure-report/data");
////       if(new File( System.getProperty("user.dir") + "/build/allure-report_2/data/test-cases").exists()){
////           folder = new File(System.getProperty("user.dir") + "/build/allure-report_2/data/test-cases");
//           for(int i = 0; i <5; i ++){
//               if(!folder.exists()){
//                   try {
//                       System.out.println("\n !!! folder check  !! " + i +"\n");
//                       Thread.sleep(1000);
//                       folder = new File(System.getProperty("user.dir")+"/build/allure-report/data");
//                   }catch(InterruptedException e){}
//               } else {
//                   System.out.println("\n !!! folder exists !! \n");
//                   break;
//               }
//           }
//           System.out.println("is folder exists: " + folder.exists() + "\n" + folder.getPath());
//           System.out.println("\n is folder data list: " +Arrays.asList(folder.listFiles()).stream()
//                   .map(e -> e.getName()).collect(Collectors.toList()).toString() + "\n");
//
//           allureTestCases= Arrays.asList(folder.listFiles());
//       }else{
           folder = new File(ALLURE_REPORT_DATA_FOLDER);
           allureTestCases = Arrays.stream(folder.listFiles())
                   .filter(file -> file.getName().contains("testcase.json"))
                   .collect(Collectors.toList());
//       }


       ObjectMapper objectMapper = new ObjectMapper();
       return allureTestCases.stream()
               .map(allureTC -> parseJSONfILE(allureTC, objectMapper))
               .collect(Collectors.toList());
   }

}
