package org.example.html.head

import org.example.html.Display
import org.example.html.IndentScope

internal data class Head(val depth: Int) : Display, IndentScope by IndentScope(depth) {
    override val displayText: String
        get() = withIndent("")
}

class HeadScope internal constructor(private val depth: Int) {
    internal fun build(): Head = Head(depth = depth)
}
