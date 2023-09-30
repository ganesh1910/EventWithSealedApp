package com.gk.eventwithsealedapp

data class MainScreenState(
    var isCountButtonVisible: Boolean = false,
    var displayingResult: String = "",
    var inputValue: String = ""
)
