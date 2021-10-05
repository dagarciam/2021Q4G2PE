package com.bbva.datioamproduct.fdevdatio

import com.bbva.datioamproduct.fdevdatio.commons.ConfigConstants._
import com.bbva.datioamproduct.fdevdatio.engine.Engine
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
   * @param spark  Initialized SparkSession
   * @param config Config retrieved from args
   */
  override def runProcess(spark: SparkSession, config: Config): Int = {
    Try {
      val devName: String = config getString DevNameConfig
      val devAge: Int = config.getInt(DevAgeConfig)
      val devRol: String = config.getString(DevSpecializationConfig)
      logger info s"Desarrollador: $devName\nEdad: $devAge\nEspecialidad: ${devRol toLowerCase}"

      val engine: Engine = new Engine(spark, config)
      engine()

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
