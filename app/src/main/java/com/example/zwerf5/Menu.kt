package com.example.zwerf5

data class Menu(
    val howtodo: String? = null,
    val img: String? = null,
    val ingredient: String? = null,
    val name: String? = null
){
    constructor():this(
        "",
        "",
        "",
        ""
    )
}

