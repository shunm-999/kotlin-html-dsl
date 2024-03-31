package org.example.ext

internal data class StringBuilderScope(val stringBuilder: StringBuilder)

internal fun StringBuilder.appendOneLine(value: String?) {
    if (value?.endsWith('\n') == true) {
        append(value)
    } else {
        append(value ?: "").append('\n')
    }
}

internal fun StringBuilder.appendOneLineIfNotBlank(value: String?) {
    if (!value.isNullOrBlank()) {
        appendOneLine(value)
    }
}

internal fun StringBuilder.appendIfNotBlank(value: String?) {
    if (!value.isNullOrBlank()) {
        append(value)
    }
}
