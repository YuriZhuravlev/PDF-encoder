package ru.zhuravlevyuri.pdfencoder.repository

import com.google.common.hash.Hashing

const val CIPHER_NAME = "Blowfish"

fun hashCipher(password: String): ByteArray = Hashing.sha384().hashUnencodedChars(password).asBytes()
fun hashKey(password: String) = Hashing.sha256().hashUnencodedChars(password).toString()