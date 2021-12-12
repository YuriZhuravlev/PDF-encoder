package ru.zhuravlevyuri.pdfencoder

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.zhuravlevyuri.pdfencoder.repository.Decode
import ru.zhuravlevyuri.pdfencoder.repository.Decode.getData
import ru.zhuravlevyuri.pdfencoder.repository.Encode
import ru.zhuravlevyuri.pdfencoder.repository.Encode.insertData
import ru.zhuravlevyuri.pdfencoder.repository.hashCipher
import ru.zhuravlevyuri.pdfencoder.repository.hashKey
import java.io.ByteArrayInputStream
import java.io.File

private const val FOLDER = "test-files"
private const val CONTAINERS = "containers"
private const val MESSAGES = "messages"

class Test {
    private val messages: File by lazy {
        File(File(FOLDER), MESSAGES)
    }
    private val containers: File by lazy {
        File(File(FOLDER), CONTAINERS)
    }

    @Test
    fun testEncodeDecode() {
        val data = File(messages, "test.pdf").readBytes()
        val key = hashCipher("password")
        val encode = Encode.encodeData(data, key)
        Assertions.assertArrayEquals(Decode.decodeData(encode, key), data)
    }

    @Test
    fun testEncodeDecode2() {
        val data = File(messages, "test.png").readBytes()
        val key = hashCipher("sd77fbhnfgd981gbdfg")
        val encode = Encode.encodeData(data, key)
        Assertions.assertArrayEquals(Decode.decodeData(encode, key), data)
    }

    @Test
    fun testEncodeDecode3() {
        val data = File(messages, "test.zip").readBytes()
        val key = hashCipher("password")
        val encode = Encode.encodeData(data, key)
        Assertions.assertArrayEquals(Decode.decodeData(encode, key), data)
    }

    @Test
    fun testEncodeDecode4() {
        val data = File(messages, "test.txt").readBytes()
        val key = hashCipher("test")
        val encode = Encode.encodeData(data, key)
        Assertions.assertArrayEquals(Decode.decodeData(encode, key), data)
    }

    @Test
    fun testEncodeDecode5() {
        val data = File(messages, "test.pdf").readBytes()
        val key = hashCipher("65__?sdf89")
        val encode = Encode.encodeData(data, key)
        Assertions.assertArrayEquals(Decode.decodeData(encode, key), data)
    }

    @Test
    fun testEncodeDecode6() {
        val data = File(messages, "test8.doc").readBytes()
        val key = hashCipher("password")
        val encode = Encode.encodeData(data, key)
        Assertions.assertArrayEquals(Decode.decodeData(encode, key), data)
    }

    @Test
    fun testInsertDataGetData() {
        val container = ByteArrayInputStream(File(containers, "test.pdf").readBytes())
        val data = File(messages, "test.pdf").readBytes()
        val keyData = hashKey("password")
        val dataMessage = ByteArrayInputStream(container.insertData(data, keyData).toByteArray()).getData(keyData)
        Assertions.assertArrayEquals(dataMessage, data)
    }

    @Test
    fun testInsertDataGetData2() {
        val container = ByteArrayInputStream(File(containers, "test.pdf").readBytes())
        val data = File(messages, "test.png").readBytes()
        val keyData = hashKey("sd77fbhnfgd981gbdfg")
        val dataMessage = ByteArrayInputStream(container.insertData(data, keyData).toByteArray()).getData(keyData)
        Assertions.assertArrayEquals(dataMessage, data)
    }

    @Test
    fun testInsertDataGetData3() {
        val container = ByteArrayInputStream(File(containers, "test2.pdf").readBytes())
        val data = File(messages, "test.zip").readBytes()
        val keyData = hashKey("password")
        val dataMessage = ByteArrayInputStream(container.insertData(data, keyData).toByteArray()).getData(keyData)
        Assertions.assertArrayEquals(dataMessage, data)
    }

    @Test
    fun testInsertDataGetData4() {
        val container = ByteArrayInputStream(File(containers, "test3.pdf").readBytes())
        val data = File(messages, "test.txt").readBytes()
        val keyData = hashKey("test")
        val dataMessage = ByteArrayInputStream(container.insertData(data, keyData).toByteArray()).getData(keyData)
        Assertions.assertArrayEquals(dataMessage, data)
    }

    @Test
    fun testInsertDataGetData5() {
        val container = ByteArrayInputStream(File(containers, "test3.pdf").readBytes())
        val data = File(messages, "test.pdf").readBytes()
        val keyData = hashKey("65__?sdf89")
        val dataMessage = ByteArrayInputStream(container.insertData(data, keyData).toByteArray()).getData(keyData)
        Assertions.assertArrayEquals(dataMessage, data)
    }

    @Test
    fun testInsertDataGetData6() {
        val container = ByteArrayInputStream(File(containers, "test3.pdf").readBytes())
        val data = File(messages, "test8.doc").readBytes()
        val keyData = hashKey("password")
        val dataMessage = ByteArrayInputStream(container.insertData(data, keyData).toByteArray()).getData(keyData)
        Assertions.assertArrayEquals(dataMessage, data)
    }

    @Test
    fun testInsertDataGetDataWithEncrypt() {
        val container = ByteArrayInputStream(File(containers, "test.pdf").readBytes())
        val data = File(messages, "test.pdf").readBytes()
        val key = hashCipher("password")
        val keyData = hashKey("password")
        val encode = Encode.encodeData(data, key)
        val dataMessage = ByteArrayInputStream(container.insertData(encode, keyData).toByteArray()).getData(keyData)
        Assertions.assertArrayEquals(Decode.decodeData(dataMessage, key), data)
    }

    @Test
    fun testInsertDataGetDataWithEncrypt2() {
        val container = ByteArrayInputStream(File(containers, "test.pdf").readBytes())
        val data = File(messages, "test.png").readBytes()
        val key = hashCipher("sd77fbhnfgd981gbdfg")
        val keyData = hashKey("sd77fbhnfgd981gbdfg")
        val encode = Encode.encodeData(data, key)
        val dataMessage = ByteArrayInputStream(container.insertData(encode, keyData).toByteArray()).getData(keyData)
        Assertions.assertArrayEquals(Decode.decodeData(dataMessage, key), data)
    }

    @Test
    fun testInsertDataGetDataWithEncrypt3() {
        val container = ByteArrayInputStream(File(containers, "test2.pdf").readBytes())
        val data = File(messages, "test.zip").readBytes()
        val key = hashCipher("password")
        val keyData = hashKey("password")
        val encode = Encode.encodeData(data, key)
        val dataMessage = ByteArrayInputStream(container.insertData(encode, keyData).toByteArray()).getData(keyData)
        Assertions.assertArrayEquals(Decode.decodeData(dataMessage, key), data)
    }

    @Test
    fun testInsertDataGetDataWithEncrypt4() {
        val container = ByteArrayInputStream(File(containers, "test3.pdf").readBytes())
        val data = File(messages, "test.txt").readBytes()
        val key = hashCipher("test")
        val keyData = hashKey("test")
        val encode = Encode.encodeData(data, key)
        val dataMessage = ByteArrayInputStream(container.insertData(encode, keyData).toByteArray()).getData(keyData)
        Assertions.assertArrayEquals(Decode.decodeData(dataMessage, key), data)
    }

    @Test
    fun testInsertDataGetDataWithEncrypt5() {
        val container = ByteArrayInputStream(File(containers, "test3.pdf").readBytes())
        val data = File(messages, "test.pdf").readBytes()
        val key = hashCipher("65__?sdf89")
        val keyData = hashKey("65__?sdf89")
        val encode = Encode.encodeData(data, key)
        val dataMessage = ByteArrayInputStream(container.insertData(encode, keyData).toByteArray()).getData(keyData)
        Assertions.assertArrayEquals(Decode.decodeData(dataMessage, key), data)
    }

    @Test
    fun testInsertDataGetDataWithEncrypt6() {
        val container = ByteArrayInputStream(File(containers, "test3.pdf").readBytes())
        val data = File(messages, "test8.doc").readBytes()
        val key = hashCipher("password")
        val keyData = hashKey("password")
        val encode = Encode.encodeData(data, key)
        val dataMessage = ByteArrayInputStream(container.insertData(encode, keyData).toByteArray()).getData(keyData)
        Assertions.assertArrayEquals(Decode.decodeData(dataMessage, key), data)
    }
}