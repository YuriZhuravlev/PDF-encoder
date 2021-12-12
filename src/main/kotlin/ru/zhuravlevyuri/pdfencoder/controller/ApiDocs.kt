package ru.zhuravlevyuri.pdfencoder.controller

import kotlinx.html.*


object ApiDocs {
    fun create(html: HTML) {
        html.apply {
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
                        tr {
                            td {
                                +"encrypt: true | false\t"
                            }
                            td {
                                +"шифровать сообщение"
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
                                +"string<binary>\t"
                            }
                            td {
                                +"файл, в который записаны данные в zip архиве"
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
                                +"string<binary>\t"
                            }
                            td {
                                +"расшифрованные данные в zip архиве"
                            }
                        }
                    }
                }
            }
        }
    }
}
