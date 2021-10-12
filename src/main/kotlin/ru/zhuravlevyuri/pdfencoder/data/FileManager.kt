package ru.zhuravlevyuri.pdfencoder.data

import java.io.File
import java.util.*

object FileManager {
    private val cache = LinkedList<File>()
    const val MAX_CACHE = 32
    private const val path = "cache/"
    private val dir = File(path)

    fun allocate(file: File) {
        if (cache.size >= MAX_CACHE) {
            println("clean cache (${cache.size})")
            for (i in 0..(MAX_CACHE / 2)) {
                cache.first.parentFile.deleteRecursively()
                cache.removeFirst()
            }
        }
        cache.addLast(file)
    }

    fun createFile(name: String): File {
        if (!dir.exists()) {
            dir.mkdirs()
        } else {
            if (cache.isEmpty()) {
                println("clean cache")
                dir.deleteRecursively()
            }
            dir.mkdirs()
        }
        val d = File(dir, Date().time.toString())
        d.mkdirs()
        return File(d, name)
    }
}
