package com.bbva.datioamproduct.fdevdatio.transformations

import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevCustomersPhones.CustomerVip
import com.bbva.datioamproduct.fdevdatio.commons.namings.{FDevCustomers, FDevPhones}
import com.bbva.datioamproduct.fdevdatio.utils.TestUtils
import org.apache.spark.sql.DataFrame

class CustomerPhonesTransformerTestMX extends TestUtils {

  "tranform method" should "return a DF with ... " in {

    //Esto es para que CustomerPhonesTransformer encuentre las llaves con las que búsca los DF
    dataReader.add(FDevCustomers.filteredKey, new CustomersTransformer(config).transform(dataReader))
    dataReader.add(FDevPhones.filteredKey, new PhonesTransformer(config).transform(dataReader))

    val outputDF: DataFrame = new CustomerPhonesTransformer(config).transform(dataReader)

    outputDF.columns should contain(CustomerVip.name)

    outputDF
      .filter(CustomerVip.column.isin("Yes", "No"))
      .count() should be(
      outputDF.count()
    )

  }

}
