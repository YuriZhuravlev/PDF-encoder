package ru.zhuravlevyuri.pdfencoder.model

import java.io.File

class RequestDecode(
    var sourceFile: File? = null,
    var password: String? = null
) {
    fun correct(): Boolean {
        return sourceFile != null && password != null
    }

    companion object {
        const val sourceFile = "source_file"
        const val password = "password"
    }
}
