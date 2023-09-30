package com.gk.eventwithsealedapp

sealed interface UIEvent {
    data class ShowMessage(val message: String) : UIEvent
}