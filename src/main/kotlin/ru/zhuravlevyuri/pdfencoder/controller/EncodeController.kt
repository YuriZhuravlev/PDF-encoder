package ru.zhuravlevyuri.pdfencoder.controller

import io.ktor.http.content.*
import ru.zhuravlevyuri.pdfencoder.model.RequestEncode
import ru.zhuravlevyuri.pdfencoder.repository.Encode.encodeData
import ru.zhuravlevyuri.pdfencoder.repository.Encode.insertData
import ru.zhuravlevyuri.pdfencoder.repository.hashCipher
import ru.zhuravlevyuri.pdfencoder.repository.hashKey
import ru.zhuravlevyuri.pdfencoder.utils.createZip

object EncodeController {
    private const val DEFAULT_NAME_FILE = "text.txt"

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
}