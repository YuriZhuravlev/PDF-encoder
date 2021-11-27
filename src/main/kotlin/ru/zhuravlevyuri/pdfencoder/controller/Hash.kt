package ru.zhuravlevyuri.pdfencoder.controller

import com.google.common.hash.Hashing

fun hashCipher(password: String) = Hashing.sha384().hashUnencodedChars(password).asBytes()
fun hashKey(password: String) = Hashing.sha256().hashUnencodedChars(password).toString()