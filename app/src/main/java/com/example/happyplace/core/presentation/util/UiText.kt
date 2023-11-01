package com.example.happyplace.core.presentation.util

import android.content.Context

sealed class UiText {

    data class StringResources(val resId: Int) : UiText()
    data class DynamicString(val message: String) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is StringResources -> context.getString(resId)
            is DynamicString -> message
        }
    }
}
