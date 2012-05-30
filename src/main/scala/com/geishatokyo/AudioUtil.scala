import java.io._
import org.jaudiotagger.audio.mp3.MP3File
import org.jaudiotagger.audio.mp4.Mp4InfoReader

/**
 * Created with IntelliJ IDEA.
 * User: Shunta
 * Date: 12/05/23
 * Time: 0:57
 * To change this template use File | Settings | File Templates.
 */

class AudioUtil {
  var format:AudioFormat.Value = _

  val m = Map[Array[Byte], AudioFormat.Value](
    Array[Byte](0x00, 0x00, 0x00, 0x20, 0x66, 0x74, 0x79, 0x70, 0x4D, 0x34, 0x41) -> AudioFormat.AAC,
    Array[Byte](0x49, 0x44, 0x33) -> AudioFormat.MP3)

  def detectFormat(audioData: Array[Byte]) : AudioFormat.Value = {
    m.foreach { case(a, v) =>
      var flag:Boolean = true
      for (i <- 0 until a.length) {
        if(a(i) != audioData(i)) {
          flag = false
        }
      }

      if (flag) return v
    }

    AudioFormat.Unknown
  }

  def getLength(audioData: Array[Byte]) : Double = {
    // [TODO] class(object) design
    val format = detectFormat(audioData)

    format match {
      case AudioFormat.AAC =>
        val aacReader = new Mp4InfoReader()
        val header = aacReader.read(new RandomAccessFile(getFileFromByteArray(audioData), "r"))
        header.getTrackLength()

      case AudioFormat.MP3 =>
        val mp3 = new MP3File(getFileFromByteArray(audioData))
        val header = mp3.getMP3AudioHeader()
        header.getTrackLength()

      case AudioFormat.Unknown =>
        Double.NaN
    }
  }

  def getFileFromByteArray(bytes: Array[Byte]) : File = {
    val tmp = File.createTempFile("mus", null)
    val fos = new FileOutputStream(tmp)
    fos.write(bytes)

    tmp
  }
}

