package com.bbva.datioamproduct.fdev.steps

import java.util.Map

import com.datio.spark.bdt.utils.Common
import com.datio.spark.logger.LazyLogging
import cucumber.api.DataTable
import cucumber.api.scala.{EN, ScalaDsl}
import org.apache.spark.sql.DataFrame
import org.scalatest.Matchers

import scala.collection.JavaConverters._

class Then extends Matchers with ScalaDsl with EN with LazyLogging {

  Then("""^(\S+) has this counts for the next filters:$""") {
    (dfName: String, dataTable: DataTable) => {
      val df: DataFrame = Common.dfMap(dfName)
      val data: List[Map[String, String]] = dataTable.asMaps(classOf[String], classOf[String]).asScala.toList

      data.foreach(record => {
        val filter: String = record.get("filter expression")
        val comparison: String = record.get("comparison")
        val expectedCount: Long = record.get("count").toLong
        val realCount: Long = df.filter(filter).count()

        withClue(s"the $dfName with the filter: $filter doesn't have $comparison $expectedCount records,") {
          comparison match {
            case "exactly" => expectedCount shouldBe realCount
            case "less than" => expectedCount should be > realCount
            case "more than" => expectedCount should be < realCount
          }
        }
      })

    }
  }

}
