package org.sisioh.scala.toolbox

import scala.util._
import scala.util.control.NonFatal
import scala.language.reflectiveCalls

object Loan {

  def using[A <: {def close()}, B](resource: A)(func: A => B): Try[B] =
    try {
      Success(func(resource))
    } catch {
      case NonFatal(e) => Failure(e)
    } finally {
      if (resource != null) resource.close()
    }

}

