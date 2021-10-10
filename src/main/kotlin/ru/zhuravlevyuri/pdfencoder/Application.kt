package ru.zhuravlevyuri.pdfencoder

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    routing {
        get("/") {
            call.respondText("<h1>PDF encoder</h1>\nHello, world!\nDEBUG=${testing}")
        }
    }
}
