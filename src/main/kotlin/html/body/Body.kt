package org.example.html.body

import org.example.html.Display
import org.example.html.IndentScope

internal data class Body(val depth: Int) : Display, IndentScope by IndentScope(depth) {
    override val displayText: String
        get() = withIndent("")
}

class BodyScope internal constructor(private val depth: Int) {
    internal fun build(): Body = Body(depth = depth)
}
