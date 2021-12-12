package ru.zhuravlevyuri.pdfencoder.repository

import com.itextpdf.kernel.pdf.*
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

object Encode {

    fun encodeData(data: ByteArray, key: ByteArray): ByteArray {
        val sks = SecretKeySpec(key, CIPHER_NAME)
        val c: Cipher = Cipher.getInstance(CIPHER_NAME)
        c.init(Cipher.ENCRYPT_MODE, sks)
        return c.doFinal(data)
    }

    private fun PdfDictionary.put(key: String, data: ByteArray) {
        put(PdfName(key), PdfStream(data))
    }

    fun InputStream.insertData(data: ByteArray, key: String): ByteArrayOutputStream {
        val outputStream = ByteArrayOutputStream()
        val document = PdfDocument(PdfReader(this), PdfWriter(outputStream))
        if (document.numberOfPages == 0) throw Exception("number of pages equal 0")
        val pdfName = PdfName(key)
        for (i in 1..document.numberOfPages) {
            val page = document.getPage(i)
            val contents = page.pdfObject.get(PdfName.Contents)
            when {
                (contents is PdfStream && contents.containsKey(pdfName)) -> {
                    contents.remove(pdfName)
                }
                (page.pdfObject.containsKey(pdfName)) -> {
                    page.pdfObject.remove(pdfName)
                }
            }
        }
        val page = Random.nextInt(1, document.numberOfPages + 1)
        document.getPage(page).let {
            val contents = it.pdfObject.get(PdfName.Contents)
            if (contents is PdfStream) {
                contents.put(key, data)
            } else {
                it.pdfObject.put(key, data)
            }
        }
        document.close()
        return outputStream
    }
}