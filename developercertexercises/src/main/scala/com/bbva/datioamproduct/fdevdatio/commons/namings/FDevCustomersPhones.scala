package com.bbva.datioamproduct.fdevdatio.commons.namings

import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones._
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions.{when, lit}

object FDevCustomersPhones {

  val key: String = "t_fdev_customersphones"

  case object CustomerVip extends Field {
    override val name = "customer_vip"

    def apply(): Column = {
      when(Prime.column === "Yes" && PriceProduct.column >= 7500, "Yes")
        .otherwise("No")
        .alias(name)
    }
  }

  case object JwkDate extends Field {
    override val name = "jwk_date"

    def apply(jwkDate: String): Column = {
      lit(jwkDate).alias(name)
    }
  }


}
