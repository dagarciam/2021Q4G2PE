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
    "com.datio.spark.bdt.steps", "com.bbva.datioamproduct.fdev.steps"
  ),
  plugin = Array(
    "pretty"
  ),
  tags = Array("~@ignore")
)
class RunCukesTest


/**
 * todo:
 *  Con spark bdt validar que la columna brand contiene las marcas Amazon, Acer... y todas las que vengan
 *  Con spark bdt validar que la columna final_price no contiene nulos
 *  Construir con Cucumber y Scala un custom step que valide que la columna X contiene o no registros con los valores dados
 *    Then the outputDF has the next values for the column brand:
 *    |value|
 *    |Acer|
 *    |Amazon|
 *    Then the outputDF doesn't have the next values for the column brand:
 *    |value|
 *    |Coolpad|
 *    |Dell|
 *
 * */

