package org.sisioh.scala.toolbox

import org.specs2.mutable.Specification
import scala.collection.mutable.ListBuffer
import org.slf4j.{LoggerFactory, Logger}

class LoggingExSpec extends Specification with LoggingEx {
  sequential

  val scopeMessage = "scopeA"
  val logMessage = "log"
  val startMessage = scopeMessage + " : start"
  val scopeLogMessage = scopeMessage + " : " + logMessage
  val endMessage = scopeMessage + " : end"

  var results: Map[String, ListBuffer[String]] = Map().withDefaultValue(ListBuffer[String]())

  def addResult(key: String, msg: String) {
    results += (key -> (results(key) :+ msg))
  }

  override val debugSingle = {
    msg: Any =>
      addResult("debug", msg.toString)
  }
  override val debugDouble = {
    (msg: Any, th: Throwable) =>
      addResult("debug", msg.toString)
  }
  override val infoSingle = {
    msg: Any =>
      addResult("info", msg.toString)
  }
  override val infoDouble = {
    (msg: Any, th: Throwable) =>
      addResult("info", msg.toString)
  }
  override val warnSingle = {
    msg: Any =>
      addResult("warn", msg.toString)
  }
  override val warnDouble = {
    (msg: Any, th: Throwable) =>
      addResult("warn", msg.toString)
  }
  override val errorSingle = {
    msg: Any =>
      addResult("error", msg.toString)
  }
  override val errorDouble = {
    (msg: Any, th: Throwable) =>
      addResult("error", msg.toString)
  }

  "LoggingEx" should {
    "output debug messages" in {
      withDebugScope(scopeMessage) {
        scopedDebug(logMessage)
      }
      results("debug")(0) must_== startMessage
      results("debug")(1) must_== scopeLogMessage
      results("debug")(2) must_== endMessage
      withDebugScope(scopeMessage, new Exception) {
        scopedDebug(logMessage, new Exception)
      }
      results("debug")(3) must_== startMessage
      results("debug")(4) must_== scopeLogMessage
      results("debug")(5) must_== endMessage
    }
    "output info messages" in {
      withInfoScope(scopeMessage) {
        scopedInfo(logMessage)
      }
      results("info")(0) must_== startMessage
      results("info")(1) must_== scopeLogMessage
      results("info")(2) must_== endMessage
      withInfoScope(scopeMessage, new Exception) {
        scopedInfo(logMessage, new Exception)
      }
      results("info")(3) must_== startMessage
      results("info")(4) must_== scopeLogMessage
      results("info")(5) must_== endMessage
    }
    "output warn messages" in {
      withWarnScope(scopeMessage) {
        scopedWarn(logMessage)
      }
      results("warn")(0) must_== startMessage
      results("warn")(1) must_== scopeLogMessage
      results("warn")(2) must_== endMessage
      withWarnScope(scopeMessage, new Exception) {
        scopedWarn(logMessage, new Exception)
      }
      results("warn")(3) must_== startMessage
      results("warn")(4) must_== scopeLogMessage
      results("warn")(5) must_== endMessage
    }
    "output error messages" in {
      withErrorScope(scopeMessage) {
        scopedError(logMessage)
      }
      results("error")(0) must_== startMessage
      results("error")(1) must_== scopeLogMessage
      results("error")(2) must_== endMessage
      withErrorScope(scopeMessage, new Exception) {
        scopedError(logMessage, new Exception)
      }
      results("error")(3) must_== startMessage
      results("error")(4) must_== scopeLogMessage
      results("error")(5) must_== endMessage
    }
  }

}
