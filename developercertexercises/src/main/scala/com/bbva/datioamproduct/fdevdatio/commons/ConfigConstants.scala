package com.bbva.datioamproduct.fdevdatio.commons

object ConfigConstants {

  val RootConfig: String = "appJob"
  val InputConfig: String = s"$RootConfig.input"
  val SongConfig: String = s"$InputConfig.song.path"
  val PokeConfig: String = s"$InputConfig.pokemons.path"

  val CustomersConfig: String = s"$InputConfig.fdev_customers.path"
  val PhonesConfig: String = s"$InputConfig.fdev_phones.path"

  val DevConfig: String = s"$RootConfig.dev"
  val DevNameConfig: String = s"$DevConfig.name"
  val DevAgeConfig: String = s"$DevConfig.age"
  val DevSpecializationConfig: String = s"$DevConfig.specialization"

  val ParamsConfig: String = s"$RootConfig.params"
  val JwkDateConfig: String = s"$ParamsConfig.jwk_date"

}
