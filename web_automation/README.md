UI tests for Area1 needs pre installed Docker https://docs.docker.com/engine/installation/
and Java 8 or upper version.

For starting tests use "./gradlew" and all tests will be started.
For specifying env use use "./gradlew -Denv=your_env". By default it is qa env


All test results will collect in Allure report.
You can find Allure report by path "build/allure-report/index.html"