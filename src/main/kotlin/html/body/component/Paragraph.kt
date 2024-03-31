package org.example.html.body.component

import org.example.html.IndentScope

internal data class Paragraph(
    private val indentScope: IndentScope,
    override val id: HtmlComponentId?,
    override val classList: List<HtmlComponentClass>,
    override val children: List<HtmlComponent>,
) : InlineHtmlComponent by InlineHtmlComponent(
        indentScope = indentScope,
        startTag = "<p>",
        endTag = "</p>",
        id = id,
        classList = classList,
        children = children,
    )
