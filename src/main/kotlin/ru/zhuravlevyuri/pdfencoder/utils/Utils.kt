package ru.zhuravlevyuri.pdfencoder.utils

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream


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

fun createZip(name: String, data: ByteArray): ByteArrayOutputStream {
    val out = ByteArrayOutputStream()
    val zip = ZipOutputStream(out)
    zip.putNextEntry(ZipEntry(name))
    zip.write(data)
    zip.closeEntry()
    zip.close()
    return out
}

fun isZip(data: ByteArray): Boolean {
    return ZipInputStream(ByteArrayInputStream(data)).nextEntry != null
}

fun unZip(input: InputStream): Pair<String, ByteArray> {
    var bytes: ByteArray? = null
    var name = "data"
    val zip = ZipInputStream(input)
    zip.nextEntry?.let { entry ->
        name = entry.name
        bytes = zip.readBytes()
    }
    zip.closeEntry()
    zip.close()
    return Pair(name, bytes ?: throw Exception("failed decode"))
}
