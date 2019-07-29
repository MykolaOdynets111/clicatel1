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