package ru.zhuravlevyuri.pdfencoder

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*


fun Routing.routeApi() {
    route("/api") {
        post("/encode/") {
            // TODO {pdf: File, encoded}
        }
        post("/decode/") {

        }
        get("/") {
            call.respondText("<h1>PDF encoder</h1>\nApi description", ContentType.Text.Html)
        }
    }
}
