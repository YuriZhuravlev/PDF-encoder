package ru.zhuravlevyuri.pdfencoder.model

import java.io.InputStream

class RequestDecode(
    var sourceFile: InputStream? = null,
    var password: String? = null
) {
    companion object {
        const val sourceFile = "source_file"
        const val password = "password"
    }
}
