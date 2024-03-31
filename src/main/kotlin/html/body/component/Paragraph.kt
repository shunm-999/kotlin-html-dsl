package org.example.html.body.component

import org.example.ext.appendOneLineIfNotBlank
import org.example.html.IndentScope

internal data class Paragraph(
    private val indentScope: IndentScope,
    override val id: HtmlComponentId?,
    override val classList: List<HtmlComponentClass>,
    override val children: List<HtmlComponent>,
) : InlineHtmlComponent, IndentScope by indentScope {
    override val displayText: String
        get() =
            buildString {
                withIndent {
                    if (attribute.isBlank()) {
                        appendOneLineWithIndent("<p>")
                    } else {
                        appendOneLineWithIndent("<p $attribute>")
                    }
                    appendOneLineIfNotBlank(children.lines)
                    appendOneLineWithIndent("</p>")
                }
            }
}
