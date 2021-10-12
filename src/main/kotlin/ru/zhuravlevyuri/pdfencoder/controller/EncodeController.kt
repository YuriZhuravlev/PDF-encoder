package ru.zhuravlevyuri.pdfencoder.controller

import io.ktor.http.content.*
import ru.zhuravlevyuri.pdfencoder.data.FileManager
import ru.zhuravlevyuri.pdfencoder.data.FileManager.createFile
import ru.zhuravlevyuri.pdfencoder.model.RequestEncode
import ru.zhuravlevyuri.pdfencoder.utils.write
import java.io.File

object EncodeController {
    suspend fun encode(multiPartData: MultiPartData): File? {
        val request = RequestEncode()
        multiPartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    when (part.name) {
                        RequestEncode.password -> request.password = part.value
                        RequestEncode.content -> request.content = part.value.byteInputStream()
                    }
                }
                is PartData.FileItem -> {
                    when (part.name) {
                        RequestEncode.sourceFile -> {
                            val name = part.originalFileName ?: throw Exception("empty filename \"source_file\"")
                            val file = createFile(name)
                            write(part.streamProvider(), file)
                            request.sourceFile = file
                        }
                        RequestEncode.content -> {
                            val name = part.originalFileName ?: throw Exception("empty filename \"content\"")
                            request.content = part.streamProvider()
                        }
                    }
                }
                is PartData.BinaryItem -> {
                    println(part.name)
                }
            }
        }
        // TODO("Создать логику шифрования")
        request.sourceFile?.let { FileManager.allocate(it) }
        return request.sourceFile
    }
}