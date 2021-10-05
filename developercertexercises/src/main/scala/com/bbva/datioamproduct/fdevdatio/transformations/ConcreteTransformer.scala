package com.bbva.datioamproduct.fdevdatio.transformations

import com.bbva.datio.datahubpe.utils.processing.data.{DataReader, DataWriter}
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import com.bbva.datioamproduct.fdevdatio.commons.namings.{FDevCustomers, FDevCustomersPhones, FDevPhones}
import com.typesafe.config.Config

class ConcreteTransformer(config: Config) extends Transformer[DataReader, DataWriter] {
  override def transform(dataReader: DataReader): DataWriter = {

    val dataWriter: DataWriter = new DataWriter()

    //Filtrado de la tabla Customers
    dataReader.add(FDevCustomers.filteredKey, new CustomersTransformer(config).transform(dataReader))

    //Filtrado de la tabla Phones
    dataReader.add(FDevPhones.filteredKey, new PhonesTransformer(config).transform(dataReader))

    //Transformaciones para generar la tabla CustomersPhones final
    dataWriter.add(
      FDevCustomersPhones.key,
      new CustomerPhonesTransformer(config).transform(dataReader)
    )

    dataWriter
  }

}
