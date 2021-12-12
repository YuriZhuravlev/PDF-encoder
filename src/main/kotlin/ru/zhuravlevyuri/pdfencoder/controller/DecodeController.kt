package ru.zhuravlevyuri.pdfencoder.controller

import io.ktor.http.content.*
import ru.zhuravlevyuri.pdfencoder.model.RequestDecode
import ru.zhuravlevyuri.pdfencoder.repository.Decode.decodeData
import ru.zhuravlevyuri.pdfencoder.repository.Decode.getData
import ru.zhuravlevyuri.pdfencoder.repository.hashCipher
import ru.zhuravlevyuri.pdfencoder.repository.hashKey

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
}
