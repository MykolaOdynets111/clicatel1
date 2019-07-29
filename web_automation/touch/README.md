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
and use "./gradlew" and all tests will be started (in order to know what tests will be started
check out the default parameter values).

## Tests run configurations

* Specific tenant
In order to start tests against some specific tenant use -Dtenant parameter ```./gradlew -Dtenant=virginmoney```
NOTE: tenants which have "-" in the tenantname should be passed with underscore "_" instead

In order to run tests against all supported tenant at once, add to the basic run command "runTestsForAllTenants"
For example, the following command will run all tests on qa env:
"./gradlew runTestsForAllTenants -Denv=qa"

For specifying the env use -Denv parameter.

For specifying the test suites testng xmls (may be found in src/test/resources/<tenantName>) and gradle tasks are used .
And in order to run particular tests suite add the following into your run command: "-Dsuite=targetsuite".
For example, the following command will run only tie tests :
"./gradlew -Dsuite=tie"

For running test in selenium grid add "-Dremote=true" to the run command

In order to specify where to run tests, active or standby, please use -Ddeploy_to parameter.
In order to run tests on "standby" pass "standby_group" value to the parameter (e.g. "./gradlew -Ddeploy_to=standby_group"

In order to run some features by tag please add the following to run command:  -Dcucumber.options='--tags @target_tag'

All test results will be collected in Allure report.
You can find Allure report by path "build/allure-report/index.html"

Default parameters' values:
-Dsuite=all
-Dtenant=all
-Denv=testing
-Dremote=false
-Ddeploy_to=active

FYI
It is also possible to attach issue to the allure report. You just need to user “@Issue” annotation.
Example:
@Issue("https://jira.clickatell.com/browse/TPORT-1916")
Scenario: Scenario name
Further more, you can attache TC id by "@TestCaseId("example-99")" annotation and
change displayed severity by using @SeverityLevel.CRITICAL annotation. Possible values are:
@SeverityLevel.BLOCKER, @SeverityLevel.CRITICAL, @SeverityLevel.NORMAL, @SeverityLevel.MINOR, @SeverityLevel.TRIVIAL

In order to run .Control tests LOCALLY, you need to start ngrock on the port, specified in
src -> main -> java -> javaserver -> Server.java -> SERVER_PORT variable
After that, paste your ngrock url into the same class in getServerURL() method

