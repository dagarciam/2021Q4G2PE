package com.bbva.datioamproduct.fdevdatio.commons.namings

import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones.Taxes.{column, name}
import org.apache.spark.sql.Column
import org.apache.spark.sql.types.{DataType, DateType, DecimalType, IntegerType}

object FDevPhones {
  val key: String = "t_fdev_phones"
  val filteredKey: String = "t_fdev_phones_filtered"

  case object Brand extends Field {
    override val name = "brand"
    override val filter: Column = {
      !column.isin("Dell", "Coolpad", "Chea", "BQ", "BLU")
    }
  }

  case object Model extends Field {
    override val name = "model"
  }

  case object OperativeSystem extends Field {
    override val name = "operative_system"
  }

  case object OsVersion extends Field {
    override val name = "os_version"
  }

  case object InternalMemory extends Field {
    override val name = "internal_memory"
  }

  case object AudioJack extends Field {
    override val name = "audio_jack"
  }

  case object Nfc extends Field {
    override val name = "nfc"
  }

  case object CustomerId extends Field {
    override val name = "customer_id"
  }

  case object CountryCode extends Field {
    override val name = "country_code"

    override val filter: Column = {
      !column.isin("CH", "IT", "CZ", "DK")
    }
  }

  case object BranchId extends Field {
    override val name = "branch_id"
  }

  case object StockNumber extends Field {
    override val name = "stock_number"
    override val dataType: DataType = IntegerType
  }

  case object SoldProductNumber extends Field {
    override val name = "sold_product_number"
  }

  case object DiscountAmount extends Field {
    override val name = "discount_amount"
    override val dataType: DataType = DecimalType(9,2)

    def apply(): Column = column.cast(dataType).alias(name)
  }

  case object Prime extends Field {
    override val name = "prime"
  }

  case object Taxes extends Field {
    override val name = "taxes"
    override val dataType: DataType = DecimalType(9,2)

    def apply(): Column = column.cast(dataType).alias(name)
  }

  case object PriceProduct extends Field {
    override val name = "price_product"
    override val dataType: DataType = DecimalType(9,2)
  }

  case object DeliveryId extends Field {
    override val name = "delivery_id"
  }

  case object PurchaseDate extends Field {
    override val name = "purchase_date"
  }

  case object CutoffDate extends Field {
    override val name = "cutoff_date"
    override val dataType: DataType = DateType

    override val filter: Column = {
      column.between("2020-03-01", "2020-03-04")
    }
  }

}
