@developercertexercises
Feature: Feature for AmazonReport

  @ignore
  Scenario: Test amazonReportJOB should result OK
    Given a config file in path src/test/resources/config/application-test.conf
    When execute main class com.bbva.datioamproduct.fdevdatio.DeveloperCertExercisesJob in Spark
    Then exit code is equal to 0

  Scenario: Test amazonReport Output DataFrame
    Given a dataframe located at path src/test/resources/data/output/parquet/t_fdev_customersphones with alias outputDF and config:
      | type    |
      | parquet |
    When I read outputDF as dataframe
    Then outputDF dataframe has exactly 1928 records

