package org.example.html.body

import org.example.ext.appendOneLineIfNotBlank
import org.example.html.Display
import org.example.html.IndentScope
import org.example.html.body.component.HtmlComponent

internal data class Body(
    private val indentScope: IndentScope,
    private val children: List<HtmlComponent>,
) : Display, IndentScope by indentScope {
    override val displayText: String
        get() =
            buildString {
                withIndent {
                    appendOneLineWithIndent("<body>")
                    appendOneLineIfNotBlank(children.lines)
                    appendOneLineWithIndent("</body>")
                }
            }
}
