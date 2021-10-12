package ru.zhuravlevyuri.pdfencoder

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.zhuravlevyuri.pdfencoder.controller.ApiDocs
import ru.zhuravlevyuri.pdfencoder.controller.DecodeController
import ru.zhuravlevyuri.pdfencoder.controller.EncodeController


fun Routing.routeApi() {
    route("/api") {
        post("/encode/") {
            try {
                val request = EncodeController.encode(call.receiveMultipart()) ?: throw Exception("error with file")
                call.respondFile(request)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "{error: " + (e.message ?: "unknown") + "}")
            }
        }
        post("/decode/") {
            try {
                val request = DecodeController.decode(call.receiveMultipart())
                call.respondFile(request ?: throw Exception("error with file"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "{error: " + (e.message ?: "unknown") + "}")
            }
        }
        get("/") {
            call.respondHtml {
                ApiDocs.create(this)
            }
        }
    }
}
