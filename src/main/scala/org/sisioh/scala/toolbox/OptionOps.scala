package org.sisioh.scala.toolbox

import scala.util._

/**
 * [[scala.Option]]を拡張するためのオブジェクト。
 */
object OptionOps {

  /**
   * [[scala.Option]]を[[scala.util.Try]]に変換するための暗黙的型変換。
   *
   * @param option 変換元の[[scala.Option]]
   * @tparam A Optionの要素型
   */
  implicit class RichOption[A](val option: Option[A]) extends AnyVal {
    def toTry: Try[A] = toTry(new NoSuchElementException)

    def toTry(throwable: scala.Throwable): Try[A] =
      Try(option.getOrElse(throw throwable))
  }

}
