package org.example.html.body

import org.example.html.Display
import org.example.html.IndentScope

data class Body(val depth: Int) : Display, IndentScope by IndentScope(depth) {
    override val displayText: String
        get() = withIndent("")
}
