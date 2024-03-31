package org.example.html

internal interface IndentScope {
    fun dig(): Int

    fun withIndent(text: String): String

    fun withIndent(builder: IndentScope.() -> Unit) {
        builder(this)
    }

    fun StringBuilder.appendLineIfNotBlank(value: String?) {
        if (!value.isNullOrBlank()) {
            appendLine(withIndent(value))
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
