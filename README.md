[![Apache 2.0 License](https://img.shields.io/badge/License-Apache%202.0-orange)](./LICENSE)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=tobias-laa_reflective-fluent-builders&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=tobias-laa_reflective-fluent-builders)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=tobias-laa_reflective-fluent-builders&metric=coverage)](https://sonarcloud.io/summary/new_code?id=tobias-laa_reflective-fluent-builders)

# Reflective Fluent Builders
This project provides a generator and a maven plugin for generating fluent builders for existing classes with the help of reflection.
This can be useful in cases where it is not possible (or very hard) to change the sources of said classes to generate builders directly.

The artifact of the corresponding maven plugin is `reflective-fluent-builders-maven-plugin`.

# Use cases and preferable alternatives
If you have full control of the sources for which you want to generate fluent builders, this project is probably not the best choice available. You might want to look at these well-maintained and stable alternatives:
- 🌶️ [Project Lombok](https://projectlombok.org/ "Project Lombok")'s [@Builder](https://projectlombok.org/features/Builder "@Builder") feature.
- [Immutables](https://immutables.github.io/)
- [@Free**Builder**](https://freebuilder.inferred.org/)

If however one of these conditions applies to your situation, this maven plugin might be useful to you:
- You want to generate builders for classes from a **third-party library**
- You cannot use the alternatives mentioned above due to **technical debt**, **constraints of your employer** or the like
- You want to generate builders for **generated sources** such as `JAXB`-annotated classes generated from an `XML` schema
- You want to generate builders for your classes in **test scope only**

# Maven Plugin usage
TODO add short description and a few examples

Full documentation of the maven plugin and its parameters can be found [here](https://tobias-laa.github.io/reflective-fluent-builders/reflective-fluent-builders-maven-plugin/reflective-fluent-builders-maven-plugin/plugin-info.html).
