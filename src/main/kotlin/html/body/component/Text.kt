package org.example.html.body.component

import org.example.html.IndentScope

internal data class Text(
    private val indentScope: IndentScope,
    val value: String,
) : HtmlComponent, IndentScope by indentScope {
    override val startTag: String = ""
    override val endTag: String? = null
    override val id: HtmlComponentId? = null
    override val classList: List<HtmlComponentClass> = emptyList()
    override val children: List<HtmlComponent> = emptyList()

    override val displayText: String
        get() =
            buildString {
                withIndent {
                    appendOneLineWithIndent(value)
                }
            }
}
