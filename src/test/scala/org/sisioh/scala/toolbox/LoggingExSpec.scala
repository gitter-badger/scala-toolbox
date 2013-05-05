package org.sisioh.scala.toolbox

import org.specs2.mutable.Specification
import scala.collection.mutable.ListBuffer

class LoggingExSpec extends Specification with LoggingEx {
  sequential

  object LogLevel extends Enumeration {
    val Debug, Info, Warn, Error = Value
  }


  val scopeMessage = "scopeA"
  val logMessage = "log"
  val startMessage = scopeMessage + " : start"
  val scopeLogMessage = scopeMessage + " : " + logMessage
  val endMessage = scopeMessage + " : end"

  var results: Map[LogLevel.Value, ListBuffer[String]] = Map().withDefaultValue(ListBuffer[String]())

  def addResult(key: LogLevel.Value, msg: String) {
    results += (key -> (results(key) :+ msg))
  }

  override val debugSingle = {
    msg: Any =>
      addResult(LogLevel.Debug, msg.toString)
  }
  override val debugDouble = {
    th: Throwable => msg: Any =>
      addResult(LogLevel.Debug, msg.toString)
  }
  override val infoSingle = {
    msg: Any =>
      addResult(LogLevel.Info, msg.toString)
  }
  override val infoDouble = {
    th: Throwable => msg: Any =>
      addResult(LogLevel.Info, msg.toString)
  }
  override val warnSingle = {
    msg: Any =>
      addResult(LogLevel.Warn, msg.toString)
  }
  override val warnDouble = {
    th: Throwable => msg: Any =>
      addResult(LogLevel.Warn, msg.toString)
  }
  override val errorSingle = {
    msg: Any =>
      addResult(LogLevel.Error, msg.toString)
  }
  override val errorDouble = {
    th: Throwable => msg: Any =>
      addResult(LogLevel.Error, msg.toString)
  }

  "LoggingEx" should {
    "output debug messages" in {
      withDebugScope(scopeMessage) {
        scopedDebug(logMessage)
      }
      results(LogLevel.Debug)(0) must_== startMessage
      results(LogLevel.Debug)(1) must_== scopeLogMessage
      results(LogLevel.Debug)(2) must_== endMessage
      withDebugScope(scopeMessage, new Exception) {
        scopedDebug(logMessage, new Exception)
      }
      results(LogLevel.Debug)(3) must_== startMessage
      results(LogLevel.Debug)(4) must_== scopeLogMessage
      results(LogLevel.Debug)(5) must_== endMessage
    }
    "output info messages" in {
      withInfoScope(scopeMessage) {
        scopedInfo(logMessage)
      }
      results(LogLevel.Info)(0) must_== startMessage
      results(LogLevel.Info)(1) must_== scopeLogMessage
      results(LogLevel.Info)(2) must_== endMessage
      withInfoScope(scopeMessage, new Exception) {
        scopedInfo(logMessage, new Exception)
      }
      results(LogLevel.Info)(3) must_== startMessage
      results(LogLevel.Info)(4) must_== scopeLogMessage
      results(LogLevel.Info)(5) must_== endMessage
    }
    "output warn messages" in {
      withWarnScope(scopeMessage) {
        scopedWarn(logMessage)
      }
      results(LogLevel.Warn)(0) must_== startMessage
      results(LogLevel.Warn)(1) must_== scopeLogMessage
      results(LogLevel.Warn)(2) must_== endMessage
      withWarnScope(scopeMessage, new Exception) {
        scopedWarn(logMessage, new Exception)
      }
      results(LogLevel.Warn)(3) must_== startMessage
      results(LogLevel.Warn)(4) must_== scopeLogMessage
      results(LogLevel.Warn)(5) must_== endMessage
    }
    "output error messages" in {
      withErrorScope(scopeMessage) {
        scopedError(logMessage)
      }
      results(LogLevel.Error)(0) must_== startMessage
      results(LogLevel.Error)(1) must_== scopeLogMessage
      results(LogLevel.Error)(2) must_== endMessage
      withErrorScope(scopeMessage, new Exception) {
        scopedError(logMessage, new Exception)
      }
      results(LogLevel.Error)(3) must_== startMessage
      results(LogLevel.Error)(4) must_== scopeLogMessage
      results(LogLevel.Error)(5) must_== endMessage
    }
  }

}
