package com.bbva.datioamproduct.fdevdatio.session04

import com.bbva.datioamproduct.fdevdatio.commons.ConfigConstants._
import com.bbva.datioamproduct.fdevdatio.commons.Common.PokeRegex
import com.typesafe.config.Config
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

class Pokemons(sc: SparkContext, config: Config) {
  val path: String = config.getString(PokeConfig)
  val linesRDD: RDD[String] = sc.textFile(path)

  def apply(): Unit = {
    val pokeRDD: RDD[Pokemon] = linesRDD
      .filter(
        /** todo: Implementar una función que compare la linea con el regex propuesto y devuelva true para
         * una línea que cumple y false para una línea que no cumple */
        line => {
          PokeRegex.pattern.matcher(line).matches()
        }
        //line => line.matches(PokeRegex.regex)
      )
      .map(line => {
        val tokens: Array[String] = line.split(",")
        Pokemon(tokens(0), tokens(1), tokens(2), tokens(3),
          tokens(4).toInt, tokens(5).toInt, tokens(6).toInt, tokens(7).toInt,
          tokens(8).toInt, tokens(9).toInt, tokens(10).toInt, tokens(11).toInt,
          tokens(12).toBoolean)
      })
    //pokeRDD foreach println
    //counterByType(pokeRDD) foreach println

    f(pokeRDD) foreach println
  }

  def counterByType(rdd: RDD[Pokemon]): RDD[(String, Int)] = {
    val rddAux: RDD[(String, Int)] = rdd
      .flatMap(pokemon => {
        Array(
          //(pokeType, counter)
          (pokemon.type1, 1),
          (pokemon.type2, 1)
        )
      })

    rddAux
      .filter {
        case (pokeType: String, counter: Int) => pokeType.length > 0
      }
      .reduceByKey(_ + _)

    /**
     * todo implementar el resto del método de tal forma que retorne el número de pokemones por cada tipo
     */
  }

  /**
   * Encontrar al pokemon de cada generación y tipo que tiene más hp
   * Cuál es mi llave?
   *  generación + type1
   * Cuál es mi operación de reduce?
   *  Comparo los valores de hp y me quedo el objeto con el valor más alto
   */
  def f(rdd: RDD[Pokemon]): RDD[Pokemon] = {
    rdd // RDD[Pokemon]
      .map(pokemon => {
        (s"${pokemon.type1}${pokemon.generation}", pokemon)
      }) //RDD[(String, Pokemon)]
      .reduceByKey((a: Pokemon, b: Pokemon) => {
        if (a.hp >= b.hp) a else b
      }) //RDD[(String, Pokemon)]
      .map {
        case (key: String, pokemon: Pokemon) => pokemon
      } //RDD[Pokemon]
  }


  /**
   * todo: Encontrar el pokemon más rápido de cada generación y tipo
   * retornar un RDD[Pokemon]
   * generation = 1, type1=Water, name=Blastoise
   * generation = 1, type1=Flying, name=Pidgeot
   * map -> reduceByKey -> map
   * Para el map
   * Cuál es mi key?
   * Qué pongo en la segunda posición de la tupla?
   * Para el reduceByKey
   * Entran dos elmentos del tipo de lo que puse en la posición 2 de la tupla
   * Cuál es la operación de reducción entre esos dos elementos?
   */


}
