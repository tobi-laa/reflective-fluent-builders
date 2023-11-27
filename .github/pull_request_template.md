Add a meaningful description of the changes introduced by this pull request. Ideally reference one or several issues
related and/or fixed by the pull request.

---

- [ ] All commit messages contain meaningful descriptions.
- [ ] All public methods, classes and interfaces have meaningful Javadoc.
- [ ] Further documentation has been added to `README.adoc` where applicable.
- [ ] Tests have been added covering the changes being made. Depending on the change this might entail unit tests,
  integration tests or both.
- [ ] Run the following command to regenerate IT samples and verify that the changes introduced are as expected:
   ```
   mvn clean verify -Pgenerate-expected-builders-for-it
   ```
- [ ] Build pipeline passes.