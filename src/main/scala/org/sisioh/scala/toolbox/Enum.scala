package org.sisioh.scala.toolbox

import scala.language.implicitConversions

/**
 * 列挙型定数を表すトレイト。
 *
 * @author j5ik2o
 */
trait EnumEntry extends Serializable with Ordered[EnumEntry] {

  private[toolbox] var _ordinal: Int = 0

  def ordinal: Int = _ordinal

  private def simpleName = getClass.getSimpleName match {
    case x if x.endsWith("$") => x.init
    case x => x
  }

  /** 宣言されているとおりの 列挙型定数の名前。 */
  val name = simpleName.substring(simpleName.lastIndexOf('$') + 1)

  override def toString = "%s:%d".format(name, ordinal)

  def compare(that: EnumEntry) = this.ordinal compare that.ordinal

}

/**
 * 列挙型のための骨格実装。
 *
 * @author j5ik2o
 */
trait Enum[T <: EnumEntry] extends Serializable {

  /**
   * 序数から列挙型定数を取得するためのファクトリメソッド。
   *
   * @param ordinal 序数
   */
  def apply(ordinal: Int): T =
    values.find(_.ordinal == ordinal).get

  /**
   * 名前から列挙型定数を取得するためのファクトリメソッド。
   *
   * @param name 名前
   */
  def apply(name: String): T =
    values.find(_.name == name).get

  /**
   * すべての列挙型定数を取得する。
   *
   * @return 列挙型定数のリスト
   */
  val values: Seq[T]

  def defineValues(entries: T*) = synchronized {
    var counter = 0
    entries.map {
      e =>
        e._ordinal = counter
        counter += 1
        e
    }
  }

}


