Tests are stored in src/main/tests/java/scenario/<tenantName>/*/*.feature

For starting tests navigate in the terminal to the project's folder (web_automation)
and use "./gradlew" and all tests will be started (in order to know what tests will be started
check out the default parameter values).

In order to start tests against some specific tenant use -Dtenant parameter (e.g. "./gradlew -Dtenant=virgin_money")
NOTE: tenants which have "-" in the tenantname should be passed with underscore "_" instead

In order to run tests against all supported tenant at once, add to the basic run command "runTestsForAllTenants"
For example, the following command will run all tests on qa env:
"./gradlew runTestsForAllTenants -Denv=qa"

For specifying the env use -Denv parameter.

For specifying the test suites testng xmls (may be found in src/test/resources/<tenantName> and gradle tasks are used .
And in order to run particular tests suite add the following into your run command: "-Dsuite=targetsuite".
For example, the following command will run only tie tests :
"./gradlew -Dsuite=tie"

For running test in selenium grid add "-Dremote=true" to the run command

In order to specify where to run tests, active or standby, please use -Ddeploy_to parameter.
In order to run tests on "standby" pass "standby_group" value to the parameter (e.g. "./gradlew -Ddeploy_to=standby_group"

All test results will collect in Allure report.
You can find Allure report by path "build/allure-report/index.html"

Default parameters' values:
-Dsuite=all
-Dtenant=generalbank
-Denv=testing
-Dremote=false
-Ddeploy_to=active