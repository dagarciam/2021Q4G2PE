package com.bbva.datioamproduct.fdevdatio.transformations

import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones.{Brand, CountryCode, CutoffDate}
import com.datio.spark.test.ContextProvider
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.{FlatSpec, Matchers}

class PhonesTransformerTest extends FlatSpec with Matchers with ContextProvider {
  val config: Config = ConfigFactory.defaultApplication()

  "transform method" should "return a DF without values Dell, Coolpad, Chea, BQ, BLU in brand column" in {
    val dataReader = new DataReader()

    val data: Seq[Row] = Seq(
      Row("MX", "Acer", "2020-03-10"),
      Row("PE", "Chea", "2020-03-01"),
      Row("CZ", "Amazon", "2020-03-01"),
      Row("MX", "Dell", "2020-03-01"),
      Row("MX", "BQ", "2020-03-01"),
      Row("MX", "Coolpad", "2020-03-01"),
      Row("BR", "BLU", "2020-03-01"),
      Row("BR", "Allview", "2020-03-02")
    )
    val schema: StructType = StructType(List(
      StructField(CountryCode.name, CountryCode.dataType),
      StructField(Brand.name, Brand.dataType),
      StructField(CutoffDate.name, StringType)
    ))
    val inputDF: DataFrame = spark.createDataFrame(sparkContext.parallelize(data), schema)
      .withColumn(CutoffDate.name, CutoffDate.column.cast(CutoffDate.dataType))
    dataReader.add(FDevPhones.key, inputDF)

    val outputDF: DataFrame = new PhonesTransformer(config).transform(dataReader)

    outputDF.count() should be(1)

  }

}
