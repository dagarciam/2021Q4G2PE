package com.bbva.datioamproduct.fdevdatio.sesion01

import scala.annotation.tailrec
import scala.collection.immutable._
import scala.collection.mutable

object Collections {

  val countriesA: List[String] = List("Perú", "España", "México", "China")

  val countriesB: List[String] = List("Brasil", "Costa Rica", "Ecuador")

  def myF(x: Int): Int = {
    x + 3
  }

  def main(args: Array[String]): Unit = {

    val countriesC: List[Int] = countriesA.:::(countriesB).map(country => {
      country.size
    })

    val countriesD: List[Int] = countriesA ::: countriesB map {
      _.size
    }
    //countriesD map myF foreach println

    //recursivePrint(countriesA ::: countriesB)

    countriesA ::: countriesB foreach { country =>
      println(
        matchingExample(country)
      )
    }

    val populationMap: mutable.Map[String, Int] = mutable.Map(
      "España" -> 46940000,
      "México" -> 127600000,
      "Perú" -> 32510000,
      "China" -> 1398000000
    )

    countriesA.foreach(country => {
      println(populationMap(country))
    })

  }

  @tailrec
  def recursivePrint(list: List[String]): Unit = {
    list match {
      case Nil => Unit
      case h :: t => {
        println(h)
        recursivePrint(t)
      }
    }
  }


  def matchingExample(country: String): String = {
    country match {
      case c: String if c.charAt(0) == 'E' => "La primera letra del país es E"
      case "Brasil" => "El pais es especificamente Brasil"
      case c: String if c.length == 6 => "El nombre del país tiene 6 caracteres"
      case _ => "Cualquier otro país"
    }
  }

  /**
   * todo: construir una función recursiva que calcule el promedio de una lista de elementos doubles
   *  List(3.5, 2.0, 5.5, 1.5) -> f -> 3.125
   */

  /**
   * todo: implementar una función recursiva que reciba una lista de nombres
   *  y devuelva el nombre con menos caracteres de la lista
   */

  /**
   * todo: implementar una función que utilizando el
   *  algoritmo de búsqueda binaria encuentre un elemento dentro de una lista.
   * */

  /**
   * todo: Implemente una función qué sea capaz de convertir entre divisas (mxn, usd, eur, sol, yen, col)
   *  por ejemplo f(10.5, "mxn", "yen") -> 57.43
   */

  // Nuevo cambio
  // Segundo cambio

}
