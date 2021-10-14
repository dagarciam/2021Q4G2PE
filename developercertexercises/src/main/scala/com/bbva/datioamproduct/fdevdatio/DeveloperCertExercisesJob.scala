package com.bbva.datioamproduct.fdevdatio

import com.bbva.datio.datahubpe.utils.processing.data.{DataReader, DataWriter}
import com.bbva.datio.datahubpe.utils.processing.flow.{FlowInitSpark, Transformer}
import com.bbva.datioamproduct.fdevdatio.transformations.ConcreteTransformer
import com.typesafe.config.Config

object DeveloperCertExercisesJob extends FlowInitSpark {
  override def getTransformer(config: Config): Transformer[DataReader, DataWriter] = {
    new ConcreteTransformer(config)
  }

}
