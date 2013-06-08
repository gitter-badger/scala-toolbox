package org.sisioh.scala.toolbox

import collection.mutable.ListBuffer
import scala.language.implicitConversions

/**
 * 列挙型定数を表すトレイト。
 *
 * @author j5ik2o
 */
trait EnumEntry extends Serializable with Ordered[EnumEntry] {

  /** 列挙定数の序数 (列挙宣言での位置)。 */
  private[toolbox] var ordinalNo: Int = _

  def ordinal = ordinalNo

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
 * 列挙型定数を定義するための要素。
 *
 * @author j5ik2o
 */
class DeclareEntry[T <: EnumEntry](enum: Enum[T]) {

  def %(value: T) = andThen(value)

  def andThen(value: T) = {
    enum.appendValue(value)
    this
  }
}

/**
 * 列挙型のための骨格実装。
 *
 * @author j5ik2o
 */
trait Enum[T <: EnumEntry] extends Serializable {
  private var counter: Int = _
  private val enums = ListBuffer.empty[T]

  implicit def enumEntryToDeclareEntry(entry: T) = declared(entry)

  private[toolbox] def appendValue(value: T) = {
    counter += 1
    value.ordinalNo = counter
    enums += value
  }

  /**
   * 列挙型定数を定義するためのメソッド。
   *
   * @param value 列挙型定数
   * @return [[org.sisioh.scala.toolbox.DeclareEntry]]
   */
  def declared(value: T): DeclareEntry[T] = {
    appendValue(value)
    new DeclareEntry(this)
  }

  /**
   * 序数から列挙型定数を取得するためのファクトリメソッド。
   *
   * @param ordinal 序数
   */
  def apply(ordinal: Int): T =
    enums.find(_.ordinal == ordinal).get

  /**
   * 名前から列挙型定数を取得するためのファクトリメソッド。
   *
   * @param name 名前
   */
  def apply(name: String): T =
    enums.find(_.name == name).get

  /**
   * すべての列挙型定数を取得する。
   *
   * @return 列挙型定数のリスト
   */
  def values: List[T] = enums.result
}
