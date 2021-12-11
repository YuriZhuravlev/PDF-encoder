package ru.zhuravlevyuri.pdfencoder.controller

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfName
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfStream
import io.ktor.http.content.*
import ru.zhuravlevyuri.pdfencoder.model.RequestDecode
import ru.zhuravlevyuri.pdfencoder.utils.isZip
import java.io.InputStream
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object DecodeController {
    suspend fun decode(
        multiPartData: MultiPartData
    ): ByteArray {
        val request = parse(multiPartData)

        return decodeData(
            data = request.sourceFile!!.getData(hashKey(request.password!!)),
            key = hashCipher(request.password!!)
        )
    }

    private fun InputStream.getData(key: String): ByteArray {
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

    private suspend fun parse(multiPartData: MultiPartData): RequestDecode {
        val request = RequestDecode()
        multiPartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    if (part.name == RequestDecode.password)
                        request.password = part.value
                }
                is PartData.FileItem -> {
                    if (part.name == RequestDecode.sourceFile) {
                        request.sourceFile = part.streamProvider()
                    }
                }
                is PartData.BinaryItem -> {
                    println(part.name)
                }
            }
        }

        if (request.sourceFile == null) throw Exception("empty \"source_file\"")
        if (request.password == null) throw Exception("empty \"password\"")
        return request
    }

    private fun decodeData(data: ByteArray, key: ByteArray): ByteArray {
        return if (isZip(data))
            data
        else {
            val sks = SecretKeySpec(key, CIPHER_NAME)
            val c: Cipher = Cipher.getInstance(CIPHER_NAME)
            c.init(Cipher.DECRYPT_MODE, sks)
            c.doFinal(data)
        }
    }
}
