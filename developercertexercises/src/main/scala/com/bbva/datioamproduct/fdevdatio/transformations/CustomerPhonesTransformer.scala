package com.bbva.datioamproduct.fdevdatio.transformations

import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import com.bbva.datioamproduct.fdevdatio.commons.Common.{JoinTypes, No}
import com.bbva.datioamproduct.fdevdatio.commons.ConfigConstants._
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevCustomers.{CustomerId => _, DeliveryId => _, _}
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevCustomersPhones._
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones._
import com.bbva.datioamproduct.fdevdatio.commons.namings.{FDevCustomers, FDevPhones}
import com.typesafe.config.Config
import org.apache.spark.sql.DataFrame

class CustomerPhonesTransformer(config: Config) extends Transformer[DataReader, DataFrame] {

  override def transform(dataReader: DataReader): DataFrame = {
    val customerPhonesDF: DataFrame =
      dataReader.get(FDevCustomers.filteredKey)
        .join(
          dataReader.get(FDevPhones.filteredKey), Seq(CustomerId.name, DeliveryId.name), JoinTypes.inner
        )

    val jwkDate: String = config.getString(JwkDateConfig)

    fitToSchema(
      customerPhonesDF
        .withColumn(CustomerVip.name, CustomerVip()) //Regla 4
        .withColumn(ExtraDiscount.name, ExtraDiscount()) //Regla 5
        .withColumn(FinalPrice.name, FinalPrice()) //Regla 6
        .withColumn(Age.name, Age()) //Regla 7
        .withColumn(BrandsTop.name, BrandsTop()) //Regla 8
        .filter(BrandsTop.filter) //Regla 8
        .withColumn(JwkDate.name, JwkDate(jwkDate)) //Regla 9
        .na.fill(No, Seq(Nfc.name)) // Regla 10
        .withColumn(Taxes.name, Taxes()) // Cast por validación de esquema
        .withColumn(DiscountAmount.name, DiscountAmount()) // Cast por validación de esquema
    )

  }

  private def fitToSchema(df: DataFrame): DataFrame = df.select(
    CityName.column, StreetName.column, CreditCardNumber.column, LastName.column, FirstName.column, Age.column, Brand.column, Model.column,
    Nfc.column, CountryCode.column, Prime.column, CustomerVip.column, Taxes.column, PriceProduct.column, DiscountAmount.column, ExtraDiscount.column,
    FinalPrice.column, BrandsTop.column, JwkDate.column)
}
