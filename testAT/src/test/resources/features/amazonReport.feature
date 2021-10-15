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
    And outputDF has this counts for the next filters:
      | filter expression | comparison | count |
      | brand="Amazon"    | exactly    | 11    |
      | country_code="PE" | less than  | 205   |
    Then the outputDF dataframe have the next values for the column brand:
      | values |
      | Acer   |
      | Amazon |
    Then the outputDF dataframe do not have the next values for the column brand:
      | values  |
      | Coolpad |
      | Dell    |
