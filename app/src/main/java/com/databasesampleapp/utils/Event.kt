package com.databasesampleapp.utils

class Event<out T>(
    private val content: T
) {
    private var hasBeenHandled: Boolean = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}
