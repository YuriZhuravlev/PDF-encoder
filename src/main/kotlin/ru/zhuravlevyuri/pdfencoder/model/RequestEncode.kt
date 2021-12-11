package ru.zhuravlevyuri.pdfencoder.model

import java.io.InputStream

class RequestEncode(
    var nameSourceFile: String? = null,
    var sourceFile: InputStream? = null,
    var password: String? = null,
    var nameContent: String? = null,
    var content: InputStream? = null,
    var requireEncrypt: Boolean = false
) {
    companion object {
        const val sourceFile = "source_file"
        const val content = "content"
        const val password = "password"
        const val requireEncrypt = "encrypt"
    }
}
