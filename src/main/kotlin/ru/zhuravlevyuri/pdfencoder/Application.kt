package ru.zhuravlevyuri.pdfencoder

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(CallLogging)

    routing {
        static("/") {
            // When running under IDEA make sure that working directory is set to this sample's project folder
            staticRootFolder = File("files")
            default("index.html")
        }
        routeApi()
        get("/") {
            call.respondRedirect("index.html")
        }
    }
}
