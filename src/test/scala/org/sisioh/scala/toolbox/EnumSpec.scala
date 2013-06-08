package org.sisioh.scala.toolbox

import org.specs2.mutable.Specification

sealed trait Fruit extends EnumEntry

object Fruit extends Enum[Fruit] {

  case object Apple extends Fruit

  case object Banana extends Fruit

  Apple % Banana

}

class EnumSpec extends Specification {

  "enum" should {
    "not equal each values" in {
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
      Fruit(1) must_== Fruit.Apple
      Fruit(2) must_== Fruit.Banana
    }
  }

}
