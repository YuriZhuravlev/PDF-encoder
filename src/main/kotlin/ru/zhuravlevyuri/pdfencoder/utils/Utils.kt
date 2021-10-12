package ru.zhuravlevyuri.pdfencoder.utils

import java.io.*


@Throws(IOException::class)
fun copy(src: File, dst: File) {
    FileInputStream(src).use { `in` ->
        FileOutputStream(dst).use { out ->
            // Transfer bytes from in to out
            val buf = ByteArray(1024)
            var len: Int
            while (`in`.read(buf).also { len = it } > 0) {
                out.write(buf, 0, len)
            }
        }
    }
}

@Throws(IOException::class)
fun write(src: InputStream, dst: File) {
    FileOutputStream(dst).use { out ->
        // Transfer bytes from in to out
        val buf = ByteArray(1024)
        var len: Int
        while (src.read(buf).also { len = it } > 0) {
            out.write(buf, 0, len)
        }
    }
}
