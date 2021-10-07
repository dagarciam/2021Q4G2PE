package com.bbva.datioamproduct.fdevdatio.commons.namings

import org.apache.spark.sql.Column
import org.apache.spark.sql.functions.{col, lit}
import org.apache.spark.sql.types.{DataType, StringType}

trait Field {
  val name: String
  lazy val column: Column = col(name)
  val dataType:DataType = StringType
  val filter: Column = lit(true)
}
