## Overview
This is a multi-module project for mc2 and touch automation.

## Sub-projects
* ### shared
Module for some helpers and useful features. Is sharred between touch and mc2 sub-project

* ### mc2
Imports shared module features. Are used for mc2 testing.

* ### touch
Imports shared and mc2 modules. Are used for touch, .Control and tie testing.

## Default task
Default task is launching touch tests.
In order to launch mc2 tests add runMC2Tests into run command with following parameters.
Like
```
./gradlew runMC2Tests -Denv=qa
```

## General features

* ### Sonar
Added integration with sonar (local one for now).
To set up sonar please follow the instructions by this link
```
 https://www.c-sharpcorner.com/article/step-by-step-sonarqube-setup-and-run-sonarqube-scanner/
```
For configuration sonar used .properties file on project root
```
sonar-project.properties
```
For starting project scanning please navigate to sonar dir and launch sonar locally. Like for mac OS:
```
/Users/tmytlovych/sonarqube-6.7.7/bin/macosx-universal-64
./sonar.sh console
```
Then use the command from project root:
```
sonar-scanner
```
After sonar finishes scanning. you will see in the console link where to find report.