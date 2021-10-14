package com.bbva.datioamproduct.fdevdatio.transformations

import com.bbva.datio.datahubpe.utils.processing.data.{DataReader, DataWriter, ItemWriter}
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevCustomers.{BirthDate, GlDate}
import com.bbva.datioamproduct.fdevdatio.commons.namings.{FDevCustomers, FDevCustomersPhones, FDevPhones}
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones.CutoffDate
import com.bbva.datioamproduct.fdevdatio.dummies.Dummies._
import com.datio.spark.test.ContextProvider
import com.typesafe.config.{Config, ConfigFactory, ConfigValueFactory}
import org.apache.spark.sql.DataFrame
import org.scalatest.{FlatSpec, Matchers}
import com.bbva.datioamproduct.fdevdatio.commons.ConfigConstants.JwkDateConfig

class ConcreteTransformerTest extends FlatSpec with Matchers with ContextProvider {
  val config: Config = ConfigFactory.defaultApplication()
    .withValue(JwkDateConfig, ConfigValueFactory.fromAnyRef("2021-10-06"))

  "transform method" should "return a DataWriter with a DF with key: t_fdev_customersphones" in {
    val dataReader = new DataReader()


    val phonesDF: DataFrame = spark.createDataFrame(sparkContext.parallelize(PhonesTransformerDummies.data), PhonesTransformerDummies.schema)
      .withColumn(CutoffDate.name, CutoffDate.column.cast(CutoffDate.dataType))
    val customersDF: DataFrame = spark.createDataFrame(sparkContext.parallelize(CustomersTransformerDummies.data), CustomersTransformerDummies.schema)
      .withColumn(BirthDate.name, BirthDate.column.cast(BirthDate.dataType))
      .withColumn(GlDate.name, GlDate.column.cast(GlDate.dataType))

    dataReader.add(FDevPhones.key, phonesDF)
    dataReader.add(FDevCustomers.key, customersDF)

    val outputDW: DataWriter = new ConcreteTransformer(config).transform(dataReader)

    outputDW.contains("t_fdev_customersphones") shouldBe true

  }

}
