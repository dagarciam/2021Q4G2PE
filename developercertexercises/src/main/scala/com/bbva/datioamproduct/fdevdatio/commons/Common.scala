package com.bbva.datioamproduct.fdevdatio.commons

import scala.util.matching.Regex

object Common {


  //Para MX
  val AZUL: String = "azul"

  // Para el resto de Geografias
  val Azul: String = "azul"
  val Red: String = "rojo"

  val PokeRegex: Regex = """(\d+)\,(.+),(.+),(.+)?,(\d+),(\d+),(\d+),(\d+),(\d+),(\d+),(\d+),(\d),(False|True)""".r

  case object JoinTypes {
    val inner: String = "inner"
  }

  val Yes = "Yes"
  val No = "No"

}
