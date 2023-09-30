package com.gk.eventwithsealedapp

sealed interface CountEvent {
    data class ValueEntered(val value: String) : CountEvent
    object CountButtonClicked : CountEvent
    object ResetButtonClicked : CountEvent
}