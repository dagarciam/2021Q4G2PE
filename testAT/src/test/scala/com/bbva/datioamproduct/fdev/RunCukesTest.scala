package com.bbva.datioamproduct.fdev

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array(
    "src/test/resources/features/amazonReport.feature"
  ),
  glue = Array(
    "com.datio.spark.bdt.steps"
  ),
  plugin = Array(
    "pretty"
  ),
  tags = Array("~@ignore")
)
class RunCukesTest

