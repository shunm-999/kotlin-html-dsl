package org.example.html

internal interface IndentScope {
    fun dig(): Int

    fun withIndent(text: String): String

    fun withIndent(builder: IndentOperatorScope.() -> Unit) {
        builder(IndentOperatorScope(this))
    }
}

internal data class IndentOperatorScope(val indentScope: IndentScope) {
    fun StringBuilder.appendOneLine(value: String?) {
        if (value?.endsWith('\n') == true) {
            append(indentScope.withIndent(value))
        } else {
            append(indentScope.withIndent(value ?: "")).append('\n')
        }
    }

    fun StringBuilder.appendOneLineIfNotBlank(value: String?) {
        if (!value.isNullOrBlank()) {
            appendOneLine(value)
        }
    }
}

private data class IndentScopeImpl(
    private val depth: Int,
    private val indentWidth: Int = 4,
) : IndentScope {
    private val indent: String
        get() = " ".repeat(depth * indentWidth)

    override fun dig(): Int = depth + 1

    override fun withIndent(text: String): String = "$indent$text"
}

internal fun IndentScope(depth: Int): IndentScope = IndentScopeImpl(depth = depth)
