package org.example.html.body.component

import org.example.html.IndentScope

internal data class OrderedList(
    private val indentScope: IndentScope,
    override val id: HtmlComponentId?,
    override val classList: List<HtmlComponentClass>,
    override val children: List<HtmlComponent>,
) : BlockHtmlComponent by BlockHtmlComponent(
        indentScope = indentScope,
        startTag = "<ol>",
        endTag = "</ol>",
        id = id,
        classList = classList,
        children = children,
    )
