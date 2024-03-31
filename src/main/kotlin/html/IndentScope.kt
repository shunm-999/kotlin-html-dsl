package org.example.html

import org.example.html.body.component.HtmlComponent

internal interface IndentScope {
    fun dig(): IndentScope

    fun withIndent(text: String): String

    fun withIndent(builder: IndentOperatorScope.() -> Unit) {
        builder(IndentOperatorScope(this))
    }
}

internal data class IndentOperatorScope(val indentScope: IndentScope) {
    fun StringBuilder.appendWithIndent(value: String) {
        append(indentScope.withIndent(value))
    }

    fun StringBuilder.appendOneLineWithIndent(value: String) {
        if (value.endsWith('\n')) {
            append(indentScope.withIndent(value))
        } else {
            append(indentScope.withIndent(value)).append('\n')
        }
    }

    fun StringBuilder.appendOneLineWithIndentIfNotBlank(value: String?) {
        if (!value.isNullOrBlank()) {
            appendOneLineWithIndent(value)
        }
    }

    fun addIndent(builder: IndentOperatorScope.() -> Unit) {
        builder(IndentOperatorScope(indentScope.dig()))
    }

    val List<HtmlComponent>.lines: String
        get() {
            return buildString {
                this@lines.forEach { component ->
                    appendOneLineWithIndent(component.displayText)
                }
            }
        }
}

private data class IndentScopeImpl(
    private val depth: Int,
    private val indentWidth: Int = 4,
) : IndentScope {
    private val indent: String
        get() = " ".repeat(depth * indentWidth)

    override fun dig(): IndentScope = IndentScopeImpl(depth + 1, indentWidth)

    override fun withIndent(text: String): String = "$indent$text"
}

internal fun IndentScope(depth: Int): IndentScope = IndentScopeImpl(depth = depth)
