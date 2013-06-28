package org.sisioh.scala.toolbox

import scala.util.Try

trait Validation[+A] {

  def fold[X](failure: Throwable => X = identity[Throwable] _, success: A => X = identity[A] _): X = this match {
    case Valid(x) => success(x)
    case Invalid(x) => failure(x)
  }

  def map[B](f: A => B): Validation[B] = this match {
    case Valid(a) => Valid(f(a))
    case Invalid(e) => Invalid(e)
  }

  def foreach[U](f: A => U): Unit = this match {
    case Valid(a) => f(a)
    case Invalid(e) => ()
  }

  def flatMap[B](f: A => Validation[B]): Validation[B] = this match {
    case Valid(a) => f(a)
    case Invalid(e) => Invalid(e)
  }

  def isSuccess: Boolean = this match {
    case Valid(a) => true
    case Invalid(e) => false
  }

  def isFailure = !isSuccess

  def toTry: Try[A] = this match {
    case Valid(a) => scala.util.Success(a)
    case Invalid(e) => scala.util.Failure(e)
  }

  def toOption: Option[A] = this match {
    case Valid(a) => Some(a)
    case Invalid(_) => None
  }

}

final case class Valid[A](a: A) extends Validation[A]

final case class Invalid[A](e: Throwable) extends Validation[A]