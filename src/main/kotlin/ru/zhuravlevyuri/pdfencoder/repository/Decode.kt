package ru.zhuravlevyuri.pdfencoder.repository

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfName
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfStream
import ru.zhuravlevyuri.pdfencoder.utils.isZip
import java.io.InputStream
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object Decode {
    fun decodeData(data: ByteArray, key: ByteArray): ByteArray {
        return if (isZip(data))
            data
        else {
            val sks = SecretKeySpec(key, CIPHER_NAME)
            val c: Cipher = Cipher.getInstance(CIPHER_NAME)
            c.init(Cipher.DECRYPT_MODE, sks)
            c.doFinal(data)
        }
    }

    fun InputStream.getData(key: String): ByteArray {
        val document = PdfDocument(PdfReader(this))
        val pdfName = PdfName(key)
        if (document.numberOfPages == 0) throw Exception("number of pages equal 0")
        for (i in 1..document.numberOfPages) {
            val page = document.getPage(i)
            val contents = page.pdfObject.get(PdfName.Contents)
            val data = when {
                (contents is PdfStream && contents.containsKey(pdfName)) -> {
                    contents.get(pdfName)
                }
                (page.pdfObject.containsKey(pdfName)) -> {
                    page.pdfObject.get(pdfName)
                }
                else -> {
                    null
                }
            }
            if (data is PdfStream) {
                return data.bytes
            }
        }
        throw Exception("encoded data not found")
    }
}