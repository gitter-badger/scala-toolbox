package org.sisioh.scala.toolbox

import org.specs2.mutable.Specification

sealed trait FruitEntry extends EnumEntry

class Fruit extends Enum[FruitEntry] {

  case object Apple extends FruitEntry

  case object Banana extends FruitEntry

  val values = defineValues(Apple, Banana)

}

object Fruit extends Fruit

class EnumSpec extends Specification {

  "enum" should {
    "not equal each values" in {
      println(Fruit.Apple, Fruit.Banana)
      Fruit.Apple must_!= Fruit.Banana
    }
    "have apple as first element" in {
      Fruit.values(0) must_== Fruit.Apple
    }
    "have banana as second element" in {
      Fruit.values(1) must_== Fruit.Banana
    }
    "get enum value by name" in {
      Fruit("Apple") must_== Fruit.Apple
      Fruit("Banana") must_== Fruit.Banana
    }
    "get enum value by ordinal" in {
      Fruit(0) must_== Fruit.Apple
      Fruit(1) must_== Fruit.Banana
    }
  }

}
