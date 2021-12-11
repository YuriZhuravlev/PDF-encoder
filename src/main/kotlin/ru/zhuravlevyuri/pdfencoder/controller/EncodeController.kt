package ru.zhuravlevyuri.pdfencoder.controller

import com.itextpdf.kernel.pdf.*
import io.ktor.http.content.*
import ru.zhuravlevyuri.pdfencoder.model.RequestEncode
import ru.zhuravlevyuri.pdfencoder.utils.createZip
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

object EncodeController {
    private const val DEFAULT_NAME_FILE = "text.txt"
    private const val PURE = "pure"

    suspend fun encode(multiPartData: MultiPartData): ByteArray {
        val request = parse(multiPartData)

        val data = createZip(
            name = request.nameContent ?: DEFAULT_NAME_FILE,
            data = request.content!!.readBytes()
        ).toByteArray().let { zip ->
            if (request.requireEncrypt)
                encodeData(
                    data = zip,
                    key = hashCipher(request.password!!)
                )
            else
                zip
        }

        return createZip(
            name = request.nameSourceFile!!,
            data = request.sourceFile!!.insertData(
                data = data,
                key = hashKey(request.password!!)
            ).toByteArray()
        ).toByteArray()
    }

    private suspend fun parse(multiPartData: MultiPartData): RequestEncode {
        val request = RequestEncode()
        multiPartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    when (part.name) {
                        RequestEncode.password -> request.password = part.value
                        RequestEncode.content -> request.content = part.value.byteInputStream()
                        RequestEncode.requireEncrypt -> request.requireEncrypt = part.value == true.toString()
                    }
                }
                is PartData.FileItem -> {
                    when (part.name) {
                        RequestEncode.sourceFile -> {
                            request.nameSourceFile = part.originalFileName
                            request.sourceFile = part.streamProvider()
                        }
                        RequestEncode.content -> {
                            request.nameContent = part.originalFileName
                            request.content = part.streamProvider()
                        }
                    }
                }
                is PartData.BinaryItem -> {
                    println(part.name)
                }
            }
        }

        if (request.sourceFile == null) throw Exception("empty \"source_file\"")
        if (request.nameSourceFile == null) throw Exception("empty filename \"source_file\"")
        if (request.content == null) throw Exception("empty \"content\"")
        if (request.password == null) throw Exception("empty \"password\"")
        return request
    }

    private fun encodeData(data: ByteArray, key: ByteArray): ByteArray {
        val sks = SecretKeySpec(key, CIPHER_NAME)
        val c: Cipher = Cipher.getInstance(CIPHER_NAME)
        c.init(Cipher.ENCRYPT_MODE, sks)
        return c.doFinal(data)
    }

    private fun PdfDictionary.put(key: String, data: ByteArray) {
        put(PdfName(key), PdfStream(data))
    }

    private fun InputStream.insertData(data: ByteArray, key: String): ByteArrayOutputStream {
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