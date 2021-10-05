package com.bbva.datioamproduct.fdevdatio.commons.namings

import com.bbva.datioamproduct.fdevdatio.commons.Common.{Yes, No}
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevCustomers.BirthDate
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones._
import org.apache.spark.sql.Column
import org.apache.spark.sql.expressions.{Window, WindowSpec}
import org.apache.spark.sql.functions.{current_date, dense_rank, floor, lit, months_between, when}
import org.apache.spark.sql.types.{DateType, DecimalType, IntegerType}

object FDevCustomersPhones {

  val key: String = "t_fdev_customersphones"

  case object CustomerVip extends Field {
    override val name = "customer_vip"

    def apply(): Column = {
      when(Prime.column === Yes && PriceProduct.column >= 7500, Yes)
        .otherwise(No)
        .alias(name)
    }
  }

  case object ExtraDiscount extends Field {
    override val name = "extra_discount"

    def apply(): Column = {
      when(
        Prime.column === Yes &&
          StockNumber.column < 35 &&
          !Brand.column.isin("XOLO", "Siemens", "Panasonic", "BlackBerry"),
        PriceProduct.column * 0.1
      ).otherwise(0.0).cast(DecimalType(9, 2)).alias(name)
    }
  }

  case object FinalPrice extends Field {
    override val name = "final_price"

    def apply(): Column = {
      (PriceProduct.column + Taxes.column - DiscountAmount.column - ExtraDiscount.column)
        .cast(DecimalType(9, 2))
        .alias(name)
    }
  }

  case object Age extends Field {
    override val name = "age"

    def apply(): Column = {

      // current_date -> devuelve la fecha actual
      // date_diff -> calcula el número de días entre dos fechas
      // months_between -> calcula el número de meses entre dos fechas
      // to_date -> transforma una columna StringType a una DateType

      // floor and ceil redondean al inferior o al superior
      // round redondea al más cercano

      floor((months_between(current_date(), BirthDate.column) / 12)).cast(IntegerType).alias(name)
    }
  }

  case object BrandsTop extends Field {
    override val name: String = "brands_top"
    override val filter: Column = column <= 50

    def apply(): Column = {
      val w: WindowSpec = Window.partitionBy(Brand.column).orderBy(FinalPrice.column.desc)
      dense_rank().over(w).alias(name)
    }
  }

  case object JwkDate extends Field {
    override val name: String = "jwk_date"

    def apply(jwkDate: String): Column = {
      lit(jwkDate).cast(DateType).alias(name)
    }
  }


}
