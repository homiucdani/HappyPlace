package com.example.happyplace.core.presentation.util

sealed class UiEvent {

    data class ShowSnackbar(val message: String) : UiEvent()
    object SaveHappyPlace : UiEvent()
}
