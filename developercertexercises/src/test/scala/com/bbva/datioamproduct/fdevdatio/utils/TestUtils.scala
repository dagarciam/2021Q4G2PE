package com.bbva.datioamproduct.fdevdatio.utils

import com.datio.spark.logger.LazyLogging
import com.datio.spark.test.ContextProvider
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{FlatSpec, Matchers}

import scala.util.{Failure, Success, Try}
import java.io.File

import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.impl.ConcreteReader

trait TestUtils extends FlatSpec with ContextProvider with Matchers with LazyLogging {
  val config: Config = buildConfFile("src/test/resources/config/application-test.conf")
  lazy val dataReader: DataReader = new ConcreteReader(spark, config).read()

  def buildConfFile(path: String): Config = {
    logger.info("Reading conf file")

    val confFile: File = Try {
      new File(path)
    } match {
      case Success(file) => file
      case Failure(exception) => throw exception
    }

    val config: Config = ConfigFactory.parseFile(confFile)
    config.entrySet().size() match {
      case 0 => throw new RuntimeException(s"File $path does not exist or file is invalid")
      case _ => config
    }

  }
}