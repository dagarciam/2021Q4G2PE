package com.bbva.datioamproduct.fdevdatio.sesion01

import scala.annotation.tailrec

object Recursion {

  def main(args: Array[String]): Unit = {
    println(factorialTail(5))

    println(fibonacci(0))
  }

  /**
   * f(0) = 1
   * f(1) = 1
   * f(5) = 5 * f(4) * f(3) * f(2) * f(1) * f(0)
   * f(5) = 120
   * *
   * f(5) = 5 * f(4)
   * f(4) = 4 * f(3)
   * f(3) = 3 * f(2)
   * f(2) = 2 * f(1)
   * f(1) = 1
   */

  def factorial(n: Int): Int = {
    n match {
      case 0 | 1 => 1
      case _ => n * factorial(n - 1)
    }
  }

  /**
   * f(5) => n=5, r=1
   * f(4) => n=4, r=5
   * f(3) => n=3, r=20
   * f(2) => n=2, r=60
   * f(1) => n=1, r=120
   */
  @tailrec
  def factorialTail(n: Int, r: Int = 1): Int = {
    if (n == 0) {
      r
    } else {
      factorialTail(n - 1, n * r)
    }
  }

  /**
   * --------- 0 1 2 3 4 5 6
   * Fibonacci 1 1 2 3 5 8 13
   * f(6) = 13
   * f(6) => n=6, aux=1, r=1
   * f(5) => n=5, aux=1, r=2
   * f(4) => n=4, aux=2, r=3
   * f(3) => n=3, aux=3, r=5
   * f(2) => n=2, aux=5, r=8
   * f(1) => n=1, aux=8, r=13
   */
  @tailrec
  def fibonacci(n: Int, aux: Int = 1, r: Int = 1): Int = {
    n match {
      case 0 | 1 => r
      case _ => fibonacci(n - 1, r, r + aux)
    }
  }

}
