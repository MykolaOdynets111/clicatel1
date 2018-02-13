All test are stored in src/main/tests/scenario.*/*.feature

For starting tests use "./gradlew" and all tests will be started.

For specifying the env use "./gradlew -Denv=your_env". By default it is testing env.

For specifying the test suites tags are used in the project (e.g. @smoke).
And in order to run particular tests suite add the following into your run command: "-Dcucumber.options="--tags @YOUR_TAG"".
For example, the following command will run only tests tagged @smoke:
"./gradlew -Dcucumber.options="--tags @smoke""

For run two or more different suites use the following: -Dcucumber.options="--tags @first_suite,@second_suite".
This will run those tests, which were tagged as @first_suite OR @second_suite

For run some very specific suite use: -Dcucumber.options="--tags @first_suite --tags @second_suite". This will run only
that suite, that has both tags.

Note, that tags from Feature is inherited by it's Scenarios

All test results will collect in Allure report.
You can find Allure report by path "build/allure-report/index.html"