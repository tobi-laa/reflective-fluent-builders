# Contributing to `reflective-fluent-builders`

`reflective-fluent-builders` is released under the Apache 2.0 license.
Read on if you would like to contribute!

## Code of Conduct

This project adheres to the Contributor Covenant [code of conduct](CODE_OF_CONDUCT.md).
By participating, you are expected to uphold this code.

## Using GitHub Issues

This project uses GitHub issues to track bugs and enhancements. Please check the following before opening an issue:
- your issue is not a duplicate/is not already addressed by another issue
- your issue affects the latest available release
- your issue cannot be solved by adjusting the
[configuration](https://tobias-laa.github.io/reflective-fluent-builders/reflective-fluent-builders-maven-plugin/plugin-info.html)

## Code Conventions and Housekeeping

None of these is essential for a pull request, but they will all help.
They can also be added after the original pull request but before a merge.

- Please add meaningful descriptions to all commit messages. Furthermore, try to keep single commits from getting too large. 
- Please add meaningful Javadoc to all public methods, classes and interfaces.
- If you feel it might make sense to do so, add further documentation to `README.md`.
- Add tests covering the changes being made. Depending on the change this might entail unit tests, integration tests or both.
  This project _tries_ to stay at 100% code coverage, so this is considered a must.
- Run the following command to regenerate IT samples and verify that the changes introduced are as expected:
   ```
   mvn clean verify -Pgenerate-expected-builders-for-it
   ```
- Keep your feature branch up-to-date with `main`.
- Multiple branches exist within this project for different Java versions:
  | Branch   | Java version |
  | -------- | ------------ |
  | `main`   | Java 11      |
  | `java8`  | Java 8       |
  | `java17` | Java 17      |
  
  After your branch has been merged to `main`, please consider creating new branches
  based on `java8` and `java17` into which you merge `main`. Adjust where necessary
  and open new pull requests for each, referencing the original pull request and issue.

## Building from Source

The project can be built from the root directory using:

```
mvn clean install
```
