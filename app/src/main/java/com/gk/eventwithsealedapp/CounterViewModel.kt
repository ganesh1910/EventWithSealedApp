package com.gk.eventwithsealedapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class CounterViewModel : ViewModel() {

    private val _screenState = mutableStateOf(
        MainScreenState(
            inputValue = "",
            displayingResult = "Total is 0.0"
        )
    )
    val screenState: State<MainScreenState> = _screenState

    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow

    private var total = 0.0

    private fun addToTotal() {
        total += _screenState.value.inputValue.toDouble()
        _screenState.value = _screenState.value.copy(
            displayingResult = "Total is $total"
        )
    }

    private fun resetTotal(){
        total = 0.0
        _screenState.value = _screenState.value.copy(
            displayingResult = "Total is $total",
            inputValue = "",
            isCountButtonVisible = false
        )
    }

    fun onEvent(event: CountEvent){
        when(event){
            is CountEvent.ValueEntered -> {
                _screenState.value = _screenState.value.copy(
                    inputValue = event.value,
                    isCountButtonVisible = true
                )
            }
            is CountEvent.CountButtonClicked -> {
                addToTotal()
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = "Value added successfully"
                        )
                    )
                }
            }
            is CountEvent.ResetButtonClicked -> {
                resetTotal()
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = "Value reset successfully"
                        )
                    )
                }
            }
        }
    }
}