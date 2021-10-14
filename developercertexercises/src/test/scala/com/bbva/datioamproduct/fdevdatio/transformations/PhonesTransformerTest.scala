package com.bbva.datioamproduct.fdevdatio.transformations

import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones.{Brand, CountryCode, CutoffDate}
import com.datio.spark.test.ContextProvider
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.{FlatSpec, Matchers}
import com.bbva.datioamproduct.fdevdatio.dummies.Dummies.PhonesTransformerDummies._

class PhonesTransformerTest extends FlatSpec with Matchers with ContextProvider {
  val config: Config = ConfigFactory.defaultApplication()

  "transform method" should "return a DF without values Dell, Coolpad, Chea, BQ, BLU in brand column" in {
    val dataReader = new DataReader()


    val inputDF: DataFrame = spark.createDataFrame(sparkContext.parallelize(data), schema)
      .withColumn(CutoffDate.name, CutoffDate.column.cast(CutoffDate.dataType))
    dataReader.add(FDevPhones.key, inputDF)

    val outputDF: DataFrame = new PhonesTransformer(config).transform(dataReader)

    outputDF.count() should be(1)

  }

}
