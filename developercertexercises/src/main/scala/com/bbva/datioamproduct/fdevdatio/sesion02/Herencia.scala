package com.bbva.datioamproduct.fdevdatio.sesion02

import com.bbva.datioamproduct.fdevdatio.sesion02.pokemons.{Gengar, Pikachu}

object Herencia {

  def main(args: Array[String]): Unit = {

    val pikachu: Pikachu = new Pikachu("Drako")
    val gengar: Gengar = new Gengar("Gengar")

    pikachu.gigaRayo(10)
    pikachu.thunderBolt()

    gengar.lick()
    gengar.shadowBall()

    val list = List(
      ("Juan", 43, 1.73),
      ("Andres", 30, 1.75),
      ("Leo", 22, 1.7),
      ("Cesar", 21, 1.69),
      ("Lucía", 23, 1.52, 50.0)
    )

    val f: (String, Int, Double) => String = (name, years, height) => {
      s"$name tiene $years años de edad y mide $height m"
    }

    val transformedList: List[Any] = list
      .map {
        case (name: String, years: Int, height: Double) if years % 3 == 0 => {
          s"$name tiene $years años de edad y mide $height m"
        }
        case (name: String, years: Int, height: Double) => {
          s"$name tiene $years años de edad (no es multiplo de 3) y mide $height m"
        }
        case (name: String, years: Int, height: Double, weight: Double) => {
          s"$name tiene $years años de edad, mide $height m y pesa $weight kg"
        }
        case _@tuple => 400
      }


    transformedList.foreach(println)

  }

}
