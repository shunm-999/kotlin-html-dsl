package org.example.html.head.component

import org.example.html.Display

internal sealed interface MetaData : Display {
    val value: String

    data class ChartSet(
        override val value: String,
    ) : MetaData {
        override val displayText: String
            get() =
                buildString {
                    appendLine("<meta charset=\"${value}\">")
                }
    }

    data class Content(
        private val key: String,
        override val value: String,
    ) : MetaData {
        override val displayText: String
            get() =
                buildString {
                    appendLine("<meta name=\"${key}\" content=\"${value}\">")
                }
    }
}
