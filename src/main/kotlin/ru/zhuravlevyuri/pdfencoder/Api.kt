package ru.zhuravlevyuri.pdfencoder

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.*


fun Routing.routeApi() {
    route("/api") {
        post("/encode/") {
            // TODO {pdf: File, encoded}
        }
        post("/decode/") {

        }
        get("/") {
            call.respondHtml {
                head {
                    title {
                        +"api docs | pdf encoder"
                    }
                }
                body {
                    h1 {
                        +"Api"
                    }
                    schemeEncode()
                    schemeDecode()
                }
            }
        }
    }
}

private fun BODY.schemeEncode() {
    h2 {
        strong {
            +"POST"
        }
        +" /api/encode/"
    }
    table {
        tr {
            td {
                +"request"
            }
            td {
                table {
                    tr {
                        td {
                            attributes["width"] = "400px"
                            +"source_file: string<binary>\t"
                        }
                        td {
                            +"файл, в который записываются данные"
                        }
                    }
                    tr {
                        td {
                            +"type: string\t"
                        }
                        td {
                            +"тип данных - enum: \"file\" или \"string\""
                        }
                    }
                    tr {
                        td {
                            +"content: string или string<binary>\t"
                        }
                        td {
                            +"шифруемые файл или строка"
                        }
                    }
                    tr {
                        td {
                            +"password: string\t"
                        }
                        td {
                            +"пароль"
                        }
                    }
                }
            }
        }
        tr {
            td {
                +"response"
            }
            td {
                table {
                    tr {
                        td {
                            attributes["width"] = "400px"
                            +"source_file: string<binary>\t"
                        }
                        td {
                            +"файл, в который записаны данные"
                        }
                    }
                }
            }
        }
    }
}


private fun BODY.schemeDecode() {
    h2 {
        strong {
            +"POST"
        }
        +" /api/decode/"
    }
    table {
        tr {
            td {
                +"request"
            }
            td {
                table {
                    tr {
                        td {
                            attributes["width"] = "400px"
                            +"source_file: string<binary>\t"
                        }
                        td {
                            +"файл, который необходимо расшифровать"
                        }
                    }
                    tr {
                        td {
                            +"password: string\t"
                        }
                        td {
                            +"пароль"
                        }
                    }
                }
            }
        }
        tr {
            td {
                +"response"
            }
            td {
                table {
                    tr {
                        td {
                            attributes["width"] = "400px"
                            +"type: string\t"
                        }
                        td {
                            +"тип данных - enum: \"file\" или \"string\""
                        }
                    }
                    tr {
                        td {
                            +"content: string или string<binary>\t"
                        }
                        td {
                            +"расшифрованные данные"
                        }
                    }
                }
            }
        }
    }
}
