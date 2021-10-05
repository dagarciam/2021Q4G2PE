package com.bbva.datioamproduct.fdevdatio.transformations

import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones.{Brand, CountryCode, CutoffDate}
import com.typesafe.config.Config
import org.apache.spark.sql.DataFrame

class PhonesTransformer(config: Config) extends Transformer[DataReader, DataFrame] {
  override def transform(dataReader: DataReader): DataFrame = {
    dataReader get FDevPhones.key filter (
      CutoffDate.filter && Brand.filter && CountryCode.filter //Regla 1
      )
  }

}
