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

    orderList(List("Patricia", "Kevin", "Leo", "Ronald", "Cesar"))
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
   * List(3.5, 2.0, 5.5, 1.5) -> f -> 3.125
   */

  /**
   * todo: implementar una función recursiva que reciba una lista de nombres
   * y devuelva el nombre con menos caracteres de la lista
   */
  /**
   * List("Patricia", "Kevin", "Leo", "Ronald", "Cesar")
   * la primera vez nombre = head
   * nombre = Patricia, size = 8
   * volver a llamar a la función con el tail de la lista
   * head = Kevin
   * 5 < 8
   * Sí, entonces hay cambio
   * nombre = Kevin, size = 5
   * volver a llamar a la función con el tail de la lista
   * head = Leo
   * 3 < 5
   * Sí, entonces hay cambio
   * nombre = Leo, size = 3
   * volver a llamar a la función con el tail de la lista
   * head = Ronald
   * 6 < 5
   * No, entonces no hay cambio
   * volver a llamar a la función con el tail de la lista
   * head = César
   * 5 < 5
   * No, entonces no hay cambio
   * volver a llamar a la función con el tail de la lista
   * La lista está vacía retornamos nombre
   */
  def shortestName(list: List[String], nombre: String = ""): String = {
    if (list.isEmpty) {
      nombre
    } else if (list.head.length < nombre.length) {
      shortestName(list.tail, list.head)
    } else {
      shortestName(list.tail, nombre)
    }
  }


  /**
   * todo: implementar una función que utilizando el
   * algoritmo de búsqueda binaria encuentre un elemento dentro de una lista.
   **/

  /**
   * todo: Implemente una función qué sea capaz de convertir entre divisas (mxn, usd, eur, sol, yen, col)
   * por ejemplo f(10.5, "mxn", "yen") -> 57.43
   */

  val currenciesMap: mutable.Map[String, Double] = mutable.Map(
    "mxn" -> 1,
    "usd" -> 20,
    "eur" -> 23,
    "sol" -> 4.89,
    "yen" -> 5.5,
    "col" -> 191
  )

  /**
   * @param value          is the money expressed in origin currency
   * @param currencyTarget is the currency type to convert
   * @param mapCurrency    is the dictionary of currency exchanges
   * @return a value origin from currency origin convert to currency target
   */
  def changeCurrency(value: Double, currencyTarget: String, mapCurrency: mutable.Map[String, Double]): Double = {
    var change: Double = 0
    val currencyValue = mapCurrency(currencyTarget)
    change = value * currencyValue
    change
  }

  // 55 sol -> yen
  // 55*4.89 = 268.95
  // 268.95 * 5.5 = 1479.225

  /**
   * 1 3 5 7 8 12 34 56 78 100
   * Necesito buscar el número 34
   * tamaño de la lista = 10
   * el elemento a la mitad de la lista = 12
   * 12 == 34 ?
   * NO
   * 34 > 12 ?
   * SÍ, entonces volvemos a llamar a la función pero con la lista del elemento 5 al 10
   * lista = 12 34 56 78 100
   * tamaño de la lista = 5
   * el elemento a la mitad de la lista = 56
   * 56 == 34 ?
   * NO
   * 34 > 56
   * NO, entonces volvemos a llamar a la función pero con la lista del elemento 0 al 2
   * lista = 12 34 56
   * tamaño de la lista = 3
   * el elemento a la mitad de la lista = 34
   * 34 == 34
   * SÍ, retorno un mensaje que diga que el elemento sí está en la lista =)
   */

  def orderList(list: List[String]): Unit = {
    println("ejercicio 1 ordena lista "+list.sortBy(_.length).map { name =>
      name.split(" ").sortWith(_.length > _.length).mkString(" ")
    })
  }


}
