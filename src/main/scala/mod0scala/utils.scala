package mod0scala

object utils {

  class AutoCloseableWrapper[A <: AutoCloseable](protected val c: A) {
    def map[B](f: (A) => B): B = {
      try {
        f(c)
      } finally {
        c.close()
      }
    }

    def foreach(f: (A) => Unit): Unit = map(f)

    // Not a proper flatMap.
    def flatMap[B](f: (A) => B): B = map(f)

    // Hack :)
    def withFilter(f: (A) => Boolean) = this
  }

  object Arm {
    def apply[A <: AutoCloseable](c: A) = new AutoCloseableWrapper(c)
  }

}
