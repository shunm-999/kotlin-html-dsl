package org.example.html.head

import org.example.html.Display
import org.example.html.IndentScope

data class Head(val depth: Int) : Display, IndentScope by IndentScope(depth) {
    override val displayText: String
        get() = withIndent("")
}
