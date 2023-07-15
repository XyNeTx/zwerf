package com.example.zwerf5

data class Ingredient(
    val id: String? = null,
    val img: String? = null,
    val name: String? = null
){
    constructor():this(
        "",
        "",
        ""
    )
}
