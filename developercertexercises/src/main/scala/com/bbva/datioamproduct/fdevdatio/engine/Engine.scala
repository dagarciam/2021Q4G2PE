package com.bbva.datioamproduct.fdevdatio.engine

import com.bbva.datioamproduct.fdevdatio.commons.Common.JoinTypes
import com.bbva.datioamproduct.fdevdatio.commons.ConfigConstants._
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevCustomersPhones.CustomerVip
import com.bbva.datioamproduct.fdevdatio.commons.namings.{FDevCustomers, FDevCustomersPhones, FDevPhones}
import com.bbva.datioamproduct.fdevdatio.commons.namings.FDevPhones._
import com.typesafe.config.{Config, ConfigValueFactory}
import org.apache.spark.sql.{Column, DataFrame, SparkSession}
import org.apache.spark.sql.functions.{col, lit}

import scala.collection.mutable

class Engine(spark: SparkSession, config: Config) {

  val flowDF: mutable.Map[String, DataFrame] = mutable.Map(
    FDevCustomers.key -> spark.read.parquet(config.getString(CustomersConfig)),
    FDevPhones.key -> spark.read.parquet(config.getString(PhonesConfig))
  )

  def apply(): Unit = {
    //flowDF("customersDF") printSchema()
    //flowDF("phonesDF").printSchema()
    //Rule 1
    flowDF.put(
      FDevPhones.key,
      filterPhones(flowDF(FDevPhones.key))
    )

    //Rule2


    //Rule3
    flowDF.put(
      FDevCustomersPhones.key,
      rule3(flowDF(FDevCustomers.key), flowDF(FDevPhones.key))
    )

    //Rule4
    flowDF.put(
      FDevCustomersPhones.key,
      rule4(flowDF(FDevCustomersPhones.key))
    )

    flowDF(FDevCustomersPhones.key).show()

  }

  /**
   *
   * @param df
   * @return a df with the transformation: cutoff_date BETWEEN 2020-03-01 AND 2020-03-04
   *         AND brand IS NOT IN ("Dell", "Coolpad", "Chea", "BQ", "BLU")
   *         AND country_code IS NOT IN (CH, IT, CZ, DK)
   */
  def filterPhones(df: DataFrame): DataFrame = {
    df
      .filter(CutoffDate.filter && Brand.filter && CountryCode.filter)
  }


  /**
   * Tipos de Join en Spark
   * inner -> A nivel de registros se queda únicamente con los que están en la intersección (los que corresponden),
   * a nivel de columnas tendremos las columnas del DataFrame Izq y DataFrame Derecho
   * outer -> A nivel de registros entrega todos los registros de un inner + los registros del lado Derecho con nulls
   * en las columnas del lado Izquierdo + los registros del lado Izquierdo con nulls en las columnas del lado derecho
   * left -> A nivel de registros conserva el número de registros del DataFrame Izquierdo
   * a nivel de columnas tendremos las columnas del DataFrame Izq y DataFrame Derecho
   * right ->  A nivel de registros conserva el número de registros del DataFrame Derecho
   * a nivel de columnas tendremos las columnas del DataFrame Izq y DataFrame Derecho
   * left_semi -> A nivel de registros vamos a tener los registros en la intersección
   * a nivel de columnas tendremos las columnas del DataFrame Izquierdo
   * left_anti -> A nivel de registros vamos a tener aquellos que no tengan correspondencia del lado Derecho
   * a nivel de columnas tendremos las columnas del DataFrame Izquierdo
   * cross -> utiliza el método crossJoin y no lleva llaves por lo que genera un producto cartesiano a nivel de registros
   * a nivel de columnas tendremos las columnas del DataFrame Izq y DataFrame Derecho
   **/
  def f(izq: DataFrame, der: DataFrame): Unit = {
    println(s"Registros del lado Izquierdo: ${izq.count()}") //15000
    println(s"Registros del lado Derecho: ${der.count()}") //8632

    izq.printSchema() //13 columnas
    der.printSchema() //18 columnas

    List("inner", "outer", "left", "right", "left_semi", "left_anti").foreach(
      joinType => {
        println(s"JointType: $joinType")
        // En SQL corresponde al uso de USING (column_name)
        val joinedDF: DataFrame = izq.join(der, Seq("customer_id", "delivery_id"), joinType)
        println(s"Registros del join: ${joinedDF.count()}") //inner = 6000
        joinedDF.printSchema() //29 columnas

        /*
        En SQL corresponde al uso de ON columna_a = columna_b...
        val joinType2:DataFrame = izq.join(der, izq("customer_id") === der("customer_id_x") && izq("delivery_id") === der("delivery_id"), "inner")
        println(s"Registros del type2: ${joinType2.count()}") //3966
        joinType2.printSchema() //31 columnas
         */
      }
    )

  }

  def rule3(customersDF: DataFrame, phonesDF: DataFrame): DataFrame = {
    customersDF
      .join(phonesDF, Seq(CustomerId.name, DeliveryId.name), JoinTypes.inner)
  }


  def rule4(df: DataFrame): DataFrame = {
    df.select(
      df.columns.map(col) :+ CustomerVip(): _*
    )
  }
}
