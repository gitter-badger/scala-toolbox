package org.sisioh.scala.toolbox

import org.specs2.mutable.Specification

class OptionOpsSpec extends Specification {

  import OptionOps._

  "Option" should {
    "convert to Try" in {
      val intSomeOp: Option[Int] = Some(1)
      intSomeOp.toTry.isSuccess must beTrue
      intSomeOp.toTry.get must_== 1
      intSomeOp.toTry.toOption must_== intSomeOp

      val intNoneOp: Option[Int] = None
      intNoneOp.toTry.isFailure must beTrue
      intNoneOp.toTry.failed.get must haveClass[NoSuchElementException]
      intNoneOp.toTry.toOption must_== intNoneOp

      val ex = new Exception
      intNoneOp.toTry(ex).failed.get must_== ex
    }
  }

}
