## Project stack
* JAVA 8
* TestNG
* Cucumber
* Rest-assured
* Allure
* Gradle

## Tests Location
Cucumber tests are stored in `touch/src/main/tests/java/scenario/<tenantName>/*/*.feature`

## Tests Starting
For starting tests navigate in the terminal to the project's folder (`web_automation`)
and use `./gradlew` and all tests will be started (in order to know what tests will be started
check out the default parameter values).

## Tests run configurations

* ### Specific tenant
In order to start tests against some specific tenant use -Dtenant parameter ```./gradlew -Dtenant=virginmoney```
NOTE: tenants which have "-" in the tenantname should be passed with underscore "_" instead
By default will run tests for all tenants

* ### Specific env
For specifying the env use -Denv parameter.
```./gradlew -env=qa```

* ### Specific suite
For specifying the test suites testng xmls (may be found in `touch/src/test/resources/<tenantName>`) and gradle tasks are used .
And in order to run particular tests suite add the following into your run command: `-Dsuite=targetsuite`.
For example, the following command will run only tie tests :
```./gradlew -Dsuite=tie```
All available suites names also may be found in `touch/build.gradle` file in runTests task.

* ### Remote run
For running test in CI on Docker Selenium cluster in selenium grid add `-Dremote=true` to the run command.
All available remote machines URLs are located in `touch/src/main/java/driverfactory/DriverFactory.java`

* ### Active | Standby
In order to specify where to run tests, active or standby, please use -Ddeploy_to parameter.
In order to run tests on "standby" pass "standby_group" value to the parameter ```./gradlew -Ddeploy_to=standby_group```

* ### Specific test run
In order to run some features by tag please add the following to run command:
```-Dcucumber.options='--tags @target_tag'```

* ### Report
All test results will be collected in Allure report.
You can find Allure report by path ```build/allure-report/index.html```

* ### Default parameters' values:
-Dsuite=all
-Dtenant=all
-Denv=testing
-Dremote=false
-Ddeploy_to=active

* ### TC details issue and template ID
It is also possible to attach issue to the allure report. You just need to user “@Issue” annotation.
 * Issue:
```
@Issue("https://jira.clickatell.com/browse/TPORT-1916")
Scenario: Scenario name
```
 * TC id
 ```
 @TestCaseId("https://jira.clickatell.com/browse/TPORT-1916")
 Scenario: Scenario name
 ```
 * Severity level:
 Change displayed severity by using @SeverityLevel.CRITICAL annotation. Possible values are:
@SeverityLevel.BLOCKER, @SeverityLevel.CRITICAL, @SeverityLevel.NORMAL, @SeverityLevel.MINOR, @SeverityLevel.TRIVIAL

## LOCAL .Control tests
In order to run .Control tests LOCALLY, you need to start ngrock on the port, specified in
```
touch/src/main/java/javaserver/Server.java/SERVER_PORT variable
 ```
After that, paste your ngrock url into the same class in getServerURL() method

