package org.example.html.body.component

import org.example.html.Display
import org.example.html.IndentScope

internal sealed interface HtmlComponent : Display {
    val id: HtmlComponentId?

    val classList: List<HtmlComponentClass>

    val children: List<HtmlComponent>

    val attribute: String
        get() =
            buildString {
                if (id != null) {
                    append("id=\"$id\"")
                }
                if (classList.isNotEmpty()) {
                    append("class=\"${classList.joinToString(" ")}\"")
                }
            }
}

internal interface InlineHtmlComponent : HtmlComponent

internal interface BlockHtmlComponent : HtmlComponent

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
