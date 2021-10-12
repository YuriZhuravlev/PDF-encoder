package ru.zhuravlevyuri.pdfencoder.model

import java.io.File
import java.io.InputStream

class RequestEncode(
    var sourceFile: File? = null,
    var password: String? = null,
    var content: InputStream? = null
) {
    fun correct(): Boolean {
        return sourceFile != null && password != null && content != null
    }

    companion object {
        const val sourceFile = "source_file"
        const val content = "content"
        const val password = "password"
    }
}
