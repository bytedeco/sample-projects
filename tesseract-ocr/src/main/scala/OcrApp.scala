import org.bytedeco.javacpp.lept.{pixRead, pixDestroy}
import org.bytedeco.javacpp.tesseract

object OcrApp extends App {

  val TESSDATA_PREFIX = "data/tesseract-ocr-3.02/"
  val lang = "eng"
  val path = "data/eurotext.png"
  val t = tesseract.TessBaseAPICreate
  val rc = tesseract.TessBaseAPIInit3(t, TESSDATA_PREFIX, lang)

  if (rc != 0) {
    tesseract.TessBaseAPIDelete(t)
    println("Init failed")
    sys.exit(3)
  }

  val image = pixRead(path)
  t.SetImage(image)

  println("Result: " + t.GetUTF8Text.getString)

  t.End
  pixDestroy(image)
}

