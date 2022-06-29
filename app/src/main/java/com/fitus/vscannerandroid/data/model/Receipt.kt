package com.fitus.vscannerandroid.data.model

data class Receipt(
    var products: MutableList<String>? = null,
    var prices: MutableList<String>? = null,
    val text: String = "",
)