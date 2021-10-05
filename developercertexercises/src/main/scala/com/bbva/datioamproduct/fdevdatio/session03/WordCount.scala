package com.bbva.datioamproduct.fdevdatio.session03

import com.bbva.datioamproduct.fdevdatio.commons.ConfigConstants.SongConfig
import com.typesafe.config.Config
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

import scala.collection.mutable

class WordCount(sc: SparkContext, config: Config) {
  val path: String = config.getString(SongConfig)
  val songRDD: RDD[String] = sc.textFile(path)

  //songRDD.foreach(println)

  def apply(): Unit = {
    val rddWords: RDD[String] = getWords(songRDD)

    val avg: Double = getAverageSize(rddWords)
    println(avg)

    val rddWordsCounted: RDD[(String, Int)] = getCounter(rddWords)

    rddWordsCounted
      .collect()
      .foreach(println)

    getCountbyLength2(rddWords)
      .collect()
      .foreach(println)
  }

  def getAverageSize(rdd: RDD[String]): Double = {
    val n: Long = rdd.count() // cuenta el número de registros en el RDD

    val sum: Int = rdd // palabra1, palabra2... palabraN
      .map(_.length) // 5,7,2,3
      .reduce((a: Int, b: Int) => { //la operación que hará entre cada grupo de 2 registros
        a + b
      })
    //5+7 = 12 <- Ejecutor 1
    //2+3 = 5 <- Ejecutor 2
    //12 + 5 = 17 <- Ejecutor 3
    // return 17
    sum.toDouble / n
  }

  def getCounter(rdd: RDD[String]): RDD[(String, Int)] = {
    rdd
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .sortBy(_._2, ascending = false)

    /**
     * ("mujer",1)
     * ("mujer",1) -> ("mujer", 2)
     * -> ("mujer", 5)
     * ("mujer",1) -> ("mujer", 3)
     * ("mujer",1)
     * ("mujer",1)
     */
  }


  def getWords(rdd: RDD[String]): RDD[String] = {
    rdd
      .map(line => {
        line.replaceAll(",", "").toLowerCase
      })
      .flatMap(_.split(" "))
      .filter(_.length > 3)

    /**
     * map realiza transformaciones de un elemento y entrega un elemento
     * RDD con 6 registros que entra a un map generará otro RDD con 6 registros
     * "En un rincón" aplicando map [["En","un","rincón"], ...] RDD[Array[String]]
     * flatMap realiza transformaciones de un elemento hacia N salidas (estas salidas deben agruparse en una colección)
     * "En un rincón" aplicando flatMap ["En", "un", "rincón"] RDD[String]
     */
  }

  /**
   * todo: Implementar un método que reciba un RDD[String]
   * y nos entregue un RDD[(lenght:Int, count:Int)], donde lenght es la longitud de cada palabra
   * y count es las veces que aparecen palabras con esa longitud
   * "mujer" "mujer" "no" "soy" "esa" "abcde" "abcde" "mujer" "mujer" -> [(6, 4), (3, 2), (2, 1)]
   */
  def getCountbyLength(rdd: RDD[String]): RDD[(Int, (Int, List[String]))] = {
    rdd
      .map(word => {
        (word.length, (1, List(word)))
      })
      .reduceByKey((a, b) => {
        //Donde ._1 representa el contador de elementos reducidos,
        // ._2 representa la lista de elementos que se han reducido
        (a._1 + b._1, a._2 ::: b._2)
      })
  }

  def getCountbyLength2(rdd: RDD[String]): RDD[(Int, List[String], Int)] = {
    rdd
      .map(word => {
        (word.length, word)
      })
      .groupByKey()
      .map {
        case (key: Int, value: Any) => {
          (key, value.toList, value.size)
        }
      }
  }

}
