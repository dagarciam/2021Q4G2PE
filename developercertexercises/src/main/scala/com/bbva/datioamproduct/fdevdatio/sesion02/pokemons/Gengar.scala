package com.bbva.datioamproduct.fdevdatio.sesion02.pokemons

import com.bbva.datioamproduct.fdevdatio.sesion02.types.Ghost

class Gengar(override val name: String) extends Pokemon with Ghost {

  override val pokemonType: String = "Ghost"

  override def tackleAttack(): Unit = {
    println("Ataque no disponible")
  }


  def lick(): Unit = {
    println("Ataque lenguetazo de Gengar")
  }

}
