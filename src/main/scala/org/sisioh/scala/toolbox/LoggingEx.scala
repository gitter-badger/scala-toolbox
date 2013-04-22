package org.sisioh.scala.toolbox

import scala.util.DynamicVariable
import scala.collection.immutable
import grizzled.slf4j.Logging

/**
 * ログ出力のためのトレイト。
 */
trait LoggingEx extends Logging {

  val infoSingle: (=> Any) => Unit = info _
  val infoDouble: (=> Any, => Throwable) => Unit = info _
  val warnSingle: (=> Any) => Unit = warn _
  val warnDouble: (=> Any, => Throwable) => Unit = warn _
  val errorSingle: (=> Any) => Unit = error _
  val errorDouble: (=> Any, => Throwable) => Unit = error _
  val debugSingle: (=> Any) => Unit = debug _
  val debugDouble: (=> Any, => Throwable) => Unit = debug _

  val isInfoFlag: Boolean = isInfoEnabled
  val isWarnFlag: Boolean = isWarnEnabled
  val isErrorFlag: Boolean = isErrorEnabled
  val isDebugFlag: Boolean = isDebugEnabled

  private val msgs = new DynamicVariable[Seq[Any]](immutable.Queue.empty)

  private def withScope[T](msg: => Any, logger: (=> Any) => Unit, f: => T): T = {
    val newMsgs = msgs.value :+ msg
    val str = newMsgs.mkString(" : ")
    logger("%s : start".format(str))
    val r = msgs.withValue(newMsgs) {
      f
    }
    logger("%s : end".format(str))
    r
  }

  private def withScope[T](msg: => Any, t: => Throwable, logger: (=> Any, => Throwable) => Unit, f: => T): T = {
    val newMsgs = msgs.value :+ msg
    val str = newMsgs.mkString(" : ")
    logger("%s : start".format(str), t)
    val r = msgs.withValue(newMsgs) {
      f
    }
    logger("%s : end".format(str), t)
    r
  }

  private def scoped[T](msg: => Any, logger: (=> Any) => Unit) {
    val newMsgs = msgs.value :+ msg
    val str = newMsgs.mkString(" : ")
    logger(str)
  }

  private def scoped[T](msg: => Any, t: => Throwable, logger: (=> Any, => Throwable) => Unit) {
    val newMsgs = msgs.value :+ msg
    val str = newMsgs.mkString(" : ")
    logger(str, t)
  }

  /**
   * INFOレベルのスコープを作成する。
   *
   * @param msg メッセージ
   * @param f 関数
   * @tparam T 関数の戻り値の型
   * @return 関数の戻り値
   */
  def withInfoScope[T](msg: => Any)(f: => T): T = if (isInfoFlag) withScope(msg, infoSingle, f) else f

  /**
   * INFOレベルのスコープを作成する。
   *
   * @param msg メッセージ
   * @param t [[java.lang.Throwable]]
   * @param f 関数
   * @tparam T 関数の戻り値の型
   * @return 関数の戻り値
   */
  def withInfoScope[T](msg: => Any, t: => Throwable)(f: => T): T = if (isInfoFlag) withScope(msg, t, infoDouble, f) else f

  /**
   * WARNレベルのスコープを作成する。
   *
   * @param msg メッセージ
   * @param f 関数
   * @tparam T 関数の戻り値の型
   * @return 関数の戻り値
   */
  def withWarnScope[T](msg: => Any)(f: => T): T = if (isWarnFlag) withScope(msg, warnSingle, f) else f

  /**
   * WARNレベルのスコープを作成する。
   *
   * @param msg メッセージ
   * @param t [[java.lang.Throwable]]
   * @param f 関数
   * @tparam T 関数の戻り値の型
   * @return 関数の戻り値
   */
  def withWarnScope[T](msg: => Any, t: => Throwable)(f: => T): T = if (isWarnFlag) withScope(msg, t, warnDouble, f) else f

  /**
   * ERRレベルのスコープを作成する。
   *
   * @param msg メッセージ
   * @param f 関数
   * @tparam T 関数の戻り値の型
   * @return 関数の戻り値
   */
  def withErrorScope[T](msg: => Any)(f: => T): T = if (isErrorFlag) withScope(msg, errorSingle, f) else f

  /**
   * ERRレベルのスコープを作成する。
   *
   * @param msg メッセージ
   * @param t [[java.lang.Throwable]]
   * @param f 関数
   * @tparam T 関数の戻り値の型
   * @return 関数の戻り値
   */
  def withErrorScope[T](msg: => Any, t: => Throwable)(f: => T): T = if (isErrorFlag) withScope(msg, t, errorDouble, f) else f

  /**
   * DEBUGレベルのスコープを作成する。
   *
   * @param msg メッセージ
   * @param f 関数
   * @tparam T 関数の戻り値の型
   * @return 関数の戻り値
   */
  def withDebugScope[T](msg: => Any)(f: => T): T = if (isDebugFlag) withScope(msg, debugSingle, f) else f

  /**
   * DEBUGレベルのスコープを作成する。
   *
   * @param msg メッセージ
   * @param t [[java.lang.Throwable]]
   * @param f 関数
   * @tparam T 関数の戻り値の型
   * @return 関数の戻り値
   */
  def withDebugScope[T](msg: => Any, t: => Throwable)(f: => T): T = if (isDebugFlag) withScope(msg, t, debugDouble, f) else f

  /**
   * スコープ内にINFOレベルのメッセージを出力する。
   *
   * @param msg メッセージ
   */
  def scopedInfo(msg: => Any): Unit =
    if (isInfoEnabled) scoped(msg, info _)

  /**
   * スコープ内にINFOレベルのメッセージを出力する。
   *
   * @param msg メッセージ
   * @param t [[java.lang.Throwable]]
   */
  def scopedInfo(msg: => Any, t: => Throwable): Unit =
    if (isInfoEnabled) scoped(msg, t, info _)

  /**
   * スコープ内にINFOレベルのメッセージを出力する。
   *
   * @param msg メッセージ
   */
  def scopedWarn(msg: => Any): Unit =
    if (isWarnEnabled) scoped(msg, warn _)

  /**
   * スコープ内にINFOレベルのメッセージを出力する。
   *
   * @param msg メッセージ
   * @param t [[java.lang.Throwable]]
   */
  def scopedWarn(msg: => Any, t: => Throwable): Unit =
    if (isWarnEnabled) scoped(msg, t, warn _)

  /**
   * スコープ内にINFOレベルのメッセージを出力する。
   *
   * @param msg メッセージ
   */
  def scopedError(msg: => Any): Unit =
    if (isErrorEnabled) scoped(msg, error _)

  /**
   * スコープ内にINFOレベルのメッセージを出力する。
   *
   * @param msg メッセージ
   * @param t [[java.lang.Throwable]]
   */
  def scopedError(msg: => Any, t: => Throwable): Unit =
    if (isErrorEnabled) scoped(msg, t, error _)

  /**
   * スコープ内にINFOレベルのメッセージを出力する。
   *
   * @param msg メッセージ
   */
  def scopedDebug(msg: => Any): Unit =
    if (isDebugEnabled) scoped(msg, debug _)

  /**
   * スコープ内にINFOレベルのメッセージを出力する。
   *
   * @param msg メッセージ
   * @param t [[java.lang.Throwable]]
   */
  def scopedDebug(msg: => Any, t: => Throwable): Unit =
    if (isDebugEnabled) scoped(msg, t, debug _)

}
