package com.bbva.datioamproduct.fdevdatio.sesion01

import com.bbva.datioamproduct.fdevdatio.commons.Common._


class Car(val color: String, val tiresNumber: Int, val passengerNames: String*) {

  println("Hola desde el constructor principal de la clase")

  def printSpecs(): Unit = {

    println(s"El auto es de color $color")
    println(s"El auto tiene $tiresNumber llantas")

    passengerNames.foreach(name => {
      println(s"$name viaja en el auto de color $color")
    })

  }

  def this(color: String, passengerNames: String*) = this(color, 4, passengerNames: _*)

  def this(passengerNames: String*) = this(Red, passengerNames: _*)

}

object Examples {
  def main(args: Array[String]): Unit = {

    val passengers: List[String] = List("Leo", "Lucia", "Diego", "Kevin")

    val carBlue: Car = new Car(passengers: _*)

    println(carBlue.printSpecs)
  }
}
