package org.example.ext

internal fun StringBuilder.appendLineIfNotBlank(value: String?) {
    if (!value.isNullOrBlank()) {
        appendLine(value)
    }
}
