package ru.zhuravlevyuri.pdfencoder.model

import java.io.InputStream

class RequestEncode(
    var nameSourceFile: String? = null,
    var sourceFile: InputStream? = null,
    var password: String? = null,
    var nameContent: String? = null,
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
