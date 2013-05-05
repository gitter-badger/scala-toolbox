package org.sisioh.scala.toolbox

import org.specs2.mutable.Specification

class LoanSpec extends Specification {

  import Loan._

  "using method" should {
    "be call close method" in {
      var flag = false
      using(new {
        def close() {
          flag = true
        }
      }) {
        cl =>
        // nothing
      }
      flag must beTrue
    }
  }


}
