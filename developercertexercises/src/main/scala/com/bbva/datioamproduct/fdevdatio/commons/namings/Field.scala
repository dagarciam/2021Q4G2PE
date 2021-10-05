package com.bbva.datioamproduct.fdevdatio.commons.namings

import org.apache.spark.sql.Column
import org.apache.spark.sql.functions.{col, lit}

trait Field {
  val name: String
  lazy val column: Column = col(name)
  val filter: Column = lit(true)
}
