package com.bbva.datioamproduct.fdevdatio.dummies

import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevCustomers.{BirthDate, CityName, CreditCardNumber, GlDate, StreetName}
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones._
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object Dummies {

  case object CustomerPhonesTranformerDummies {
    val dataCustomers: Seq[Row] = Seq(
      Row("1010", "1010", "2000-10-05")
    )
    val dataPhones: Seq[Row] = Seq(
      Row("1010", "1010", "Yes", "7501.00", 10, "27.17", "5.05", "2000-10-05", "Amazon", null)
    )
    val schemaCustomer: StructType = StructType(List(
      StructField(DeliveryId.name, DeliveryId.dataType),
      StructField(CustomerId.name, CustomerId.dataType),
      StructField(BirthDate.name, StringType)
    ))
    val schemaPhones: StructType = StructType(List(
      StructField(DeliveryId.name, DeliveryId.dataType),
      StructField(CustomerId.name, CustomerId.dataType),
      StructField(Prime.name, Prime.dataType),
      StructField(PriceProduct.name, StringType),
      StructField(StockNumber.name, StockNumber.dataType),
      StructField(Taxes.name, StringType),
      StructField(DiscountAmount.name, StringType),
      StructField(Brand.name, Brand.dataType),
      StructField(Nfc.name, Nfc.dataType)
    ))
  }

  case object PhonesTransformerDummies {
    val data: Seq[Row] = Seq(
      Row("1010", "1010", "MX", "Acer", "2020-03-10", 30, 10.00, 5.0, 7501.00),
      Row("1010", "1010", "PE", "Chea", "2020-03-01", 30, 10.00, 5.0, 7501.00),
      Row("1010", "1010", "CZ", "Amazon", "2020-03-01", 30, 10.00, 5.0, 7501.00),
      Row("1010", "1010", "MX", "Dell", "2020-03-01", 30, 10.00, 5.0, 7501.00),
      Row("1010", "1010", "MX", "BQ", "2020-03-01", 30, 10.00, 5.0, 7501.00),
      Row("1010", "1010", "MX", "Coolpad", "2020-03-01", 30, 10.00, 5.0, 7501.00),
      Row("1010", "1010", "BR", "BLU", "2020-03-01", 30, 10.00, 5.0, 7501.00),
      Row("1010", "1010", "BR", "Allview", "2020-03-02", 30, 10.00, 5.0, 7501.00)
    )
    val schema: StructType = StructType(List(
      StructField(DeliveryId.name, DeliveryId.dataType),
      StructField(CustomerId.name, CustomerId.dataType),
      StructField(CountryCode.name, CountryCode.dataType),
      StructField(Brand.name, Brand.dataType),
      StructField(CutoffDate.name, StringType),
      StructField(StockNumber.name, StockNumber.dataType),
      StructField(Taxes.name, Taxes.dataType),
      StructField(DiscountAmount.name, DiscountAmount.dataType),
      StructField(PriceProduct.name, PriceProduct.dataType)
    ))
  }

  case object CustomersTransformerDummies {
    val data: Seq[Row] = Seq(
      Row("1010", "1010", "2000-10-05", "2020-03-01", "1234567890123", "Yes", "CDMX", "Revolución")
    )
    val schema: StructType = StructType(List(
      StructField(DeliveryId.name, DeliveryId.dataType),
      StructField(CustomerId.name, CustomerId.dataType),
      StructField(BirthDate.name, StringType),
      StructField(GlDate.name, StringType),
      StructField(CreditCardNumber.name, CreditCardNumber.dataType),
      StructField(Prime.name, Prime.dataType),
      StructField(CityName.name, CityName.dataType),
      StructField(StreetName.name, StreetName.dataType)
    ))
  }

}
