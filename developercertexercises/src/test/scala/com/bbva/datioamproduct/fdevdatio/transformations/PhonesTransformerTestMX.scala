package com.bbva.datioamproduct.fdevdatio.transformations

import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones.Brand
import com.bbva.datioamproduct.fdevdatio.utils.TestUtils
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col

class PhonesTransformerTestMX extends TestUtils {

  "transform method" should
    "return a DF without values Dell, Coolpad, Chea, BQ, BLU in brand column" in {

    val outputDF: DataFrame = new PhonesTransformer(config).transform(dataReader)

    outputDF.filter(
      col(Brand.name).isin("Dell", "Coolpad", "Chea", "BQ", "BLUE")
    ).count() should be(0)
  }

}
