package com.bbva.datioamproduct.fdevdatio.transformations

import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import com.bbva.datioamproduct.fdevdatio.commons.Common.JoinTypes
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevCustomersPhones.{CustomerVip, JwkDate}
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones._
import com.bbva.datioamproduct.fdevdatio.commons.namings.{FDevCustomers, FDevPhones}
import com.typesafe.config.Config
import org.apache.spark.sql.expressions.{Window, WindowSpec}
import org.apache.spark.sql.functions.{avg, col, dense_rank, rank, row_number, stddev, sum}
import org.apache.spark.sql.{Column, DataFrame}

class CustomerPhonesTransformer(config: Config) extends Transformer[DataReader, DataFrame] {

  override def transform(dataReader: DataReader): DataFrame = {
    val customerPhonesDF: DataFrame = join(
      dataReader.get(FDevCustomers.filteredKey),
      dataReader.get(FDevPhones.filteredKey)
    )
    val jwkDate: String = config.getString("appJob.params.jwk_date")


    val df: DataFrame = customerPhonesDF
      .select(
        customerPhonesDF.columns.map(col) :+ CustomerVip() :+ JwkDate(jwkDate): _*
      )

    /** -------------------------------------------------------------- */
    df
      .groupBy(CountryCode.column, Brand.column) // retorna un RelationalGroupedDataSet
      .agg(
        sum(PriceProduct.column).alias("sum"),
        avg(PriceProduct.column).alias("with_group_by"),
        stddev(PriceProduct.column).alias("stddev")
      ) // retorna un DF
      .show()

    val w: WindowSpec = Window
      .partitionBy(CountryCode.column)
      .orderBy(StockNumber.column.desc)

    val rn: Column = row_number().over(w).alias("rn")
    val rankColumn: Column = rank().over(w).alias("rank")
    val denseRank: Column = dense_rank().over(w).alias("dense_rank")

    df
      .select(
        df.columns.map(col) :+ rn :+ rankColumn :+ denseRank: _*
      ).show()

    /**
     * Funciones de agregación en spark sql
     * max, count, min, avg, mean, sum, [row_number, rank, dense_rank]
     *
     * Formas de aplicar agregaciones:
     * usando GroupBy + agg || over Window
     * GroupBy + Left Join = over Window
     *
     * Para usar row_number, rank y dense rank debemos ordenar además de particionar una window
     * partition, value,  row_number, rank, dense_rank
     * 5,         10      1           1     1
     * 5,         12      2           2     2
     * 6,         12      1           1     1
     * 6,         13      2           2     2
     * 6,         15      3           3     3
     * 6,         15      4           3     3
     * 6,         15      5           3     3
     * 6,         17      6           6     4
     *
     *
     */
    df
  }

  private def join(customersDF: DataFrame, phonesDF: DataFrame): DataFrame = {
    customersDF
      .join(phonesDF, Seq(CustomerId.name, DeliveryId.name), JoinTypes.inner)
  }
}
