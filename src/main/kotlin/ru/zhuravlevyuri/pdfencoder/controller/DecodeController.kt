package ru.zhuravlevyuri.pdfencoder.controller

import io.ktor.http.content.*
import ru.zhuravlevyuri.pdfencoder.model.RequestDecode
import ru.zhuravlevyuri.pdfencoder.utils.write
import java.io.File

object DecodeController {
    suspend fun decode(
        multiPartData: MultiPartData
    ): File? {
        val request = RequestDecode()
        multiPartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    if (part.name == RequestDecode.password)
                        request.password = part.value
                }
                is PartData.FileItem -> {
                    if (part.name == RequestDecode.sourceFile) {
                        val name = part.originalFileName ?: throw Exception("empty filename \"source_file\"")
                        val file = File(name)
                        if (!file.exists())
                            file.createNewFile()
                        write(part.streamProvider(), file)
                        request.sourceFile = file
                    }
                }
                is PartData.BinaryItem -> {
                    println(part.name)
                }
            }
        }
        // TODO
        return request.sourceFile
    }
}
