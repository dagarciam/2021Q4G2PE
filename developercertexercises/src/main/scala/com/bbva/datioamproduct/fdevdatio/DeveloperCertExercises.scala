package com.bbva.datioamproduct.fdevdatio

import com.bbva.datioamproduct.fdevdatio.sesion01.Car
import com.datio.spark.InitSpark
import com.datio.spark.metric.model.BusinessInformation
import com.typesafe.config.Config
import org.apache.spark.sql.SparkSession

import scala.util.{Failure, Success, Try}

/**
 * Main file for DeveloperCertExercises process.
 * Implements InitSpark which includes metrics and SparkSession.
 *
 * Configuration for this class should be expressed in HOCON like this:
 *
 * DeveloperCertExercises {
 * ...
 * }
 *
 */
protected trait DeveloperCertExercisesTrait extends InitSpark {
  this: InitSpark =>
  /**
   * @param sparkS Initialized SparkSession
   * @param config Config retrieved from args
   */
  override def runProcess(sparkS: SparkSession, config: Config): Int = {

    Try {

      val devName: String = config getString "2021q4g2.dev.name"
      val devAge: Int = config.getInt("2021q4g2.dev.age")
      val devRol: String = config.getString("2021q4g2.dev.specialization")

      logger info s"Desarrollador: $devName\nEdad: $devAge\nEspecialidad: ${devRol toLowerCase}"

    } match {
      case Success(value) => 0
      case Failure(e: Exception) => {
        e.printStackTrace()
        -1
      }
    }

  }

  override def defineBusinessInfo(config: Config): BusinessInformation =
    BusinessInformation(exitCode = 0, entity = "", path = "", mode = "",
      schema = "", schemaVersion = "", reprocessing = "")

}

object DeveloperCertExercises extends DeveloperCertExercisesTrait
