package com.bbva.datioamproduct.fdevdatio.sesion02.pokemons

import com.bbva.datioamproduct.fdevdatio.sesion02.types.Electric

class Pikachu(override val name: String) extends Pokemon with Electric {

  override val pokemonType: String = "electrico"

  override def tackleAttack(): Unit = {
    println("Embestida al estilo de Pikachu")
  }

  def sayPikaPika(): Unit = {
    println("pika pika, pikachu")
  }

  def gigaRayo(): Unit = {
    println("Ataque Giga Rayo")
  }

  def gigaRayo(x: Int): Unit = {

    val f: (Int) => Unit = (n: Int) => {
      println(s"Ataque Giga Rayo [$n]")
    }

    (0 until x) foreach f

  }

}
