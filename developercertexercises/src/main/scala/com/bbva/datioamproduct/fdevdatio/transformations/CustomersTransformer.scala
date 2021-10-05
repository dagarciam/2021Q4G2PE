package com.bbva.datioamproduct.fdevdatio.transformations

import com.bbva.datio.datahubpe.utils.processing.data.{DataReader, DataWriter}
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevCustomers
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevCustomers.{CreditCardNumber, GlDate}
import com.typesafe.config.Config
import org.apache.spark.sql.DataFrame

class CustomersTransformer(config: Config) extends Transformer[DataReader, DataFrame] {
  override def transform(dataReader: DataReader): DataFrame = {
    dataReader get FDevCustomers.key filter
      (GlDate.filter && CreditCardNumber.filter)
  }
}
