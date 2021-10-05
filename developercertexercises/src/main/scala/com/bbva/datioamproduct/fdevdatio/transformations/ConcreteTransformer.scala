package com.bbva.datioamproduct.fdevdatio.transformations

import com.bbva.datio.datahubpe.utils.processing.data.{DataReader, DataWriter}
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import com.bbva.datioamproduct.fdevdatio.commons.Common.JoinTypes
import com.bbva.datioamproduct.fdevdatio.commons.namings.{FDevCustomers, FDevCustomersPhones, FDevPhones}
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevCustomersPhones.CustomerVip
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones.{CustomerId, DeliveryId}
import com.typesafe.config.Config
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col

class ConcreteTransformer(config: Config) extends Transformer[DataReader, DataWriter] {
  override def transform(dataReader: DataReader): DataWriter = {

    val dataWriter: DataWriter = new DataWriter()

    //Filtrado de la tabla Customers
    dataReader.add(FDevCustomers.filteredKey, new CustomersTransformer(config).transform(dataReader))

    //Filtrado de la tabla Phones
    dataReader.add(FDevPhones.filteredKey, new PhonesTransformer(config).transform(dataReader))

    //Transformaciones para generar la tabla CustomersPhones final
    dataReader.add(
      FDevCustomersPhones.key,
      new CustomerPhonesTransformer(config) transform dataReader
    )

    dataReader get FDevCustomersPhones.key show

    dataWriter
  }

}
