package org.example.html.body.component

import org.example.ext.appendOneLineIfNotBlank
import org.example.html.Display
import org.example.html.IndentScope

internal sealed interface HtmlComponent : Display, IndentScope {
    val startTag: String

    val endTag: String?

    val id: HtmlComponentId?

    val classList: List<HtmlComponentClass>

    val children: List<HtmlComponent>

    val attribute: String
        get() =
            buildString {
                if (id != null) {
                    append("id=\"${id?.value}\"")
                    if (classList.isNotEmpty()) {
                        append(" ")
                    }
                }
                if (classList.isNotEmpty()) {
                    append("class=\"${classList.joinToString(" "){it.value}}\"")
                }
            }

    override val displayText: String
        get() =
            buildString {
                withIndent {
                    if (attribute.isBlank()) {
                        appendOneLineWithIndent(startTag)
                    } else {
                        val splits = startTag.split(">")
                        val prefix = splits[0]
                        appendOneLineWithIndent("$prefix $attribute>")
                    }
                    val childrenString = children.lines
                    appendOneLineIfNotBlank(childrenString)
                    endTag?.let {
                        appendOneLineWithIndent(it)
                    }
                }
            }
}

internal interface InlineHtmlComponent : HtmlComponent

private data class InlineHtmlComponentImpl(
    private val indentScope: IndentScope,
    override val startTag: String,
    override val endTag: String?,
    override val id: HtmlComponentId?,
    override val classList: List<HtmlComponentClass>,
    override val children: List<HtmlComponent>,
) : InlineHtmlComponent, IndentScope by indentScope

internal fun InlineHtmlComponent(
    indentScope: IndentScope,
    startTag: String,
    endTag: String?,
    id: HtmlComponentId?,
    classList: List<HtmlComponentClass>,
    children: List<HtmlComponent>,
): InlineHtmlComponent =
    InlineHtmlComponentImpl(
        indentScope = indentScope,
        startTag = startTag,
        endTag = endTag,
        id = id,
        classList = classList,
        children = children,
    )

internal interface BlockHtmlComponent : HtmlComponent

private data class BlockHtmlComponentImpl(
    private val indentScope: IndentScope,
    override val startTag: String,
    override val endTag: String?,
    override val id: HtmlComponentId?,
    override val classList: List<HtmlComponentClass>,
    override val children: List<HtmlComponent>,
) : BlockHtmlComponent, IndentScope by indentScope

internal fun BlockHtmlComponent(
    indentScope: IndentScope,
    startTag: String,
    endTag: String?,
    id: HtmlComponentId?,
    classList: List<HtmlComponentClass>,
    children: List<HtmlComponent>,
): BlockHtmlComponent =
    BlockHtmlComponentImpl(
        indentScope = indentScope,
        startTag = startTag,
        endTag = endTag,
        id = id,
        classList = classList,
        children = children,
    )

internal data class HtmlComponentId(val value: String)

internal data class HtmlComponentClass(val value: String)

data class HtmlComponentScope internal constructor(
    private val indentScope: IndentScope,
) : IndentScope by indentScope {
    private val _children: MutableList<HtmlComponent> = mutableListOf()
    internal val children: List<HtmlComponent> = _children

    fun String.html() {
        _children.add(
            Text(
                indentScope = this@HtmlComponentScope,
                value = this,
            ),
        )
    }

    fun p(
        id: String? = null,
        classes: List<String> = emptyList(),
        builder: HtmlComponentScope.() -> Unit,
    ) {
        val scope = HtmlComponentScope(dig())
        builder(scope)
        _children.add(
            Paragraph(
                indentScope = this,
                id = id?.toHtmlId(),
                classList = classes.toHtmlClass(),
                children = scope.children,
            ),
        )
    }

    fun div(
        id: String? = null,
        classes: List<String> = emptyList(),
        builder: HtmlComponentScope.() -> Unit,
    ) {
        val scope = HtmlComponentScope(dig())
        builder(scope)
        _children.add(
            Div(
                indentScope = this,
                id = id?.toHtmlId(),
                classList = classes.toHtmlClass(),
                children = scope.children,
            ),
        )
    }
}

private fun String.toHtmlId(): HtmlComponentId = HtmlComponentId(this)

private fun String.toHtmlClass(): HtmlComponentClass = HtmlComponentClass(this)

private fun List<String>.toHtmlClass(): List<HtmlComponentClass> = this.map { it.toHtmlClass() }
