package com.bbva.datioamproduct.fdevdatio.transformations

import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datioamproduct.fdevdatio.commons.ConfigConstants.JwkDateConfig
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevCustomers.BirthDate
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevCustomersPhones.CustomerVip
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones.{DiscountAmount, PriceProduct, Taxes}
import com.bbva.datioamproduct.fdevdatio.commons.namings.{FDevCustomers, FDevPhones}
import com.bbva.datioamproduct.fdevdatio.dummies.Dummies.CustomerPhonesTranformerDummies._
import com.datio.spark.test.ContextProvider
import com.typesafe.config.{Config, ConfigFactory, ConfigValueFactory}
import org.apache.spark.sql.DataFrame
import org.scalatest.{FlatSpec, Matchers}

class CustomerPhonesTransformerTest extends FlatSpec with Matchers with ContextProvider {

  var config: Config = _

  "tranform method" should "return a DF with ... " in {
    val dataReader = new DataReader()

    val customerDF: DataFrame = spark
      .createDataFrame(sparkContext.parallelize(dataCustomers), schemaCustomer)
      .withColumn(BirthDate.name, BirthDate.column.cast(BirthDate.dataType))

    val phonesDF: DataFrame = spark
      .createDataFrame(sparkContext.parallelize(dataPhones), schemaPhones)
      .withColumn(PriceProduct.name, PriceProduct.column.cast(PriceProduct.dataType))
      .withColumn(Taxes.name, Taxes.column.cast(Taxes.dataType))
      .withColumn(DiscountAmount.name, DiscountAmount.column.cast(DiscountAmount.dataType))

    dataReader.add(FDevPhones.filteredKey, phonesDF)
    dataReader.add(FDevCustomers.filteredKey, customerDF)
    config = ConfigFactory.defaultApplication()
      .withValue(JwkDateConfig, ConfigValueFactory.fromAnyRef("2021-10-06"))

    val outputDF: DataFrame = new CustomerPhonesTransformer(config).transform(dataReader)

    outputDF.columns should contain(CustomerVip.name)

    outputDF.filter(CustomerVip.column === "Yes").count() should be(1)
  }

}
