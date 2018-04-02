All test are stored in src/main/tests/scenario.*/*.feature

For starting tests use "./gradlew" and all tests will be started.

For specifying the env use "./gradlew -Denv=your_env". By default it is testing env.

For specifying the test suites testng xmls are used (may be found in src/test/resources).
And in order to run particular tests suite add the following into your run command: "-Dsuite=targetsuite".
For example, the following command will run only tie tests :
"./gradlew -Dsuite=tie"

For running test in selenium grid add "-Dremote=true" to the run command

All test results will collect in Allure report.
You can find Allure report by path "build/allure-report/index.html"