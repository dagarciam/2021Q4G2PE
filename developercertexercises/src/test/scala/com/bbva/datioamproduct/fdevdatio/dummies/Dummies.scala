package com.bbva.datioamproduct.fdevdatio.dummies

import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevCustomers.BirthDate
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

}
