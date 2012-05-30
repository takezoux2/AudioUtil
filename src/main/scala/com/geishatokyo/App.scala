import java.io.{File, ByteArrayInputStream, FileInputStream}

/**
 * Created with IntelliJ IDEA.
 * User: Shunta
 * Date: 12/05/23
 * Time: 20:17
 * To change this template use File | Settings | File Templates.
 */

object App {
  def main(args: Array[String]) {
    val a = readAllBytesFromFile("test")

    val au = new AudioUtil
    println(au.detectFormat(a))
    println(au.getLength(a))
  }

  def readAllBytesFromFile(fileName:String) : Array[Byte] = {
    val f = new File(fileName)
    val buf = new Array[Byte](f.length().toInt)
    val fis = new FileInputStream(f)
    fis.read(buf)
    buf
  }
}
