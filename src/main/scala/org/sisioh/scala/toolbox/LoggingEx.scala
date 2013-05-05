package org.sisioh.scala.toolbox

import scala.util.DynamicVariable
import scala.collection.immutable
import grizzled.slf4j.Logging

/**
 * ログ出力のためのトレイト。
 */
trait LoggingEx extends Logging {

  private[toolbox] val infoSingle: (Any) => Unit = info(_)
  private[toolbox] val infoDouble: (Any, Throwable) => Unit = {
    (msg, th) => info(msg, th)
  }
  private[toolbox] val warnSingle: (Any) => Unit = warn(_)
  private[toolbox] val warnDouble: (Any, Throwable) => Unit = {
    (msg, th) => warn(msg, th)
  }
  private[toolbox] val errorSingle: (Any) => Unit = error(_)
  private[toolbox] val errorDouble: (Any, Throwable) => Unit = {
    (msg, th) => error(msg, th)
  }
  private[toolbox] val debugSingle: (Any) => Unit = debug(_)
  private[toolbox] val debugDouble: (Any, Throwable) => Unit = {
    (msg, th) => debug(msg, th)
  }

  val isInfoFlag: Boolean = isInfoEnabled
  val isWarnFlag: Boolean = isWarnEnabled
  val isErrorFlag: Boolean = isErrorEnabled
  val isDebugFlag: Boolean = isDebugEnabled

  private val msgs = new DynamicVariable[Seq[String]](immutable.Queue.empty)

  private def withScope[T](msg: => Any, logger: (Any) => Unit, f: => T): T = {
    val newMsgs = msgs.value :+ msg.toString
    val str = newMsgs.mkString(" : ")
    logger("%s : start".format(str))
    val r = msgs.withValue(newMsgs) {
      f
    }
    logger("%s : end".format(str))
    r
  }

  private def withScope[T](msg: => Any, t: => Throwable, logger: (Any, Throwable) => Unit, f: => T): T = {
    val newMsgs = msgs.value :+ msg.toString
    val str = newMsgs.mkString(" : ")
    logger("%s : start".format(str), t)
    val r = msgs.withValue(newMsgs) {
      f
    }
    logger("%s : end".format(str), t)
    r
  }

  private def scoped[T](msg: => Any, logger: (Any) => Unit) {
    val newMsgs = msgs.value :+ msg.toString
    val str = newMsgs.mkString(" : ")
    logger(str)
  }

  private def scoped[T](msg: => Any, t: => Throwable, logger: (Any, Throwable) => Unit) {
    val newMsgs = msgs.value :+ msg.toString
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
    if (isInfoFlag) scoped(msg, infoSingle)

  /**
   * スコープ内にINFOレベルのメッセージを出力する。
   *
   * @param msg メッセージ
   * @param t [[java.lang.Throwable]]
   */
  def scopedInfo(msg: => Any, t: => Throwable): Unit =
    if (isInfoFlag) scoped(msg, t, infoDouble)

  /**
   * スコープ内にINFOレベルのメッセージを出力する。
   *
   * @param msg メッセージ
   */
  def scopedWarn(msg: => Any): Unit =
    if (isWarnFlag) scoped(msg, warnSingle)

  /**
   * スコープ内にINFOレベルのメッセージを出力する。
   *
   * @param msg メッセージ
   * @param t [[java.lang.Throwable]]
   */
  def scopedWarn(msg: => Any, t: => Throwable): Unit =
    if (isWarnFlag) scoped(msg, t, warnDouble)

  /**
   * スコープ内にINFOレベルのメッセージを出力する。
   *
   * @param msg メッセージ
   */
  def scopedError(msg: => Any): Unit =
    if (isErrorFlag) scoped(msg, errorSingle)

  /**
   * スコープ内にINFOレベルのメッセージを出力する。
   *
   * @param msg メッセージ
   * @param t [[java.lang.Throwable]]
   */
  def scopedError(msg: => Any, t: => Throwable): Unit =
    if (isErrorFlag) scoped(msg, t, errorDouble)

  /**
   * スコープ内にINFOレベルのメッセージを出力する。
   *
   * @param msg メッセージ
   */
  def scopedDebug(msg: => Any): Unit =
    if (isDebugFlag) scoped(msg, debugSingle)

  /**
   * スコープ内にINFOレベルのメッセージを出力する。
   *
   * @param msg メッセージ
   * @param t [[java.lang.Throwable]]
   */
  def scopedDebug(msg: => Any, t: => Throwable): Unit =
    if (isDebugFlag) scoped(msg, t, debugDouble)

}
