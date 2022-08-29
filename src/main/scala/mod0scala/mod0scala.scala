import java.io.PrintWriter

package object mod0scala {
  val SPEED_OF_LIGHT = 299792458

  implicit def toFunkyString(s: String) = new {
    def writeToFs(path: String) = new PrintWriter(path) { write(s); close }
  }

  def using[A, B <: {def close(): Unit}] (closeable: B) (f: B => A): A =
    try { f(closeable) } finally { closeable.close() }



}
