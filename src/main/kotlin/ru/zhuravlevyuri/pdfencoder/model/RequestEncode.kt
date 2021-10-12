package ru.zhuravlevyuri.pdfencoder.model

import java.io.File

class RequestEncode(
    var sourceFile: File? = null,
    var password: String? = null,
    var content: File? = null,
    var contentString: String? = null
) {
    fun correct(): Boolean {
        return sourceFile != null && password != null && (content != null || contentString != null)
    }

    companion object {
        const val sourceFile = "source_file"
        const val content = "content"
        const val password = "password"
    }
}
