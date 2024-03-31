package org.example.html

import org.example.ext.appendLineIfNotBlank
import org.example.html.body.Body
import org.example.html.body.BodyScope
import org.example.html.head.Head
import org.example.html.head.HeadScope

data class Html internal constructor(
    private val language: Language,
    private val head: Head,
    private val body: Body,
) : Display {
    override val displayText: String
        get() {
            return buildString {
                appendLineIfNotBlank("<!DOCTYPE html>")
                appendLineIfNotBlank("<html lang=\"${language.displayText}\">")
                appendLineIfNotBlank(head.displayText)
                appendLineIfNotBlank(body.displayText)
                appendLineIfNotBlank("</html>")
            }
        }
}

enum class Language {
    Ja,
    En,
    Zh,
    Ko,
    De,
    Fr,
    It,
    Ru,
}

class HtmlScope internal constructor() : IndentScope by IndentScope(0) {
    private var head: Head? = null
    private var body: Body? = null
    var language: Language? = null

    fun build(): Html =
        Html(
            language = language ?: throw IllegalStateException("Language is not defined"),
            head = head ?: throw IllegalStateException("Head is not defined"),
            body = body ?: throw IllegalStateException("Body is not defined"),
        )

    fun head(builder: HeadScope.() -> Unit) {
        val scope = HeadScope(depth = dig())
        builder(scope)
        head = scope.build()
    }

    fun body(builder: BodyScope.() -> Unit) {
        val scope = BodyScope(depth = dig())
        builder(scope)
        body = scope.build()
    }
}

fun html(builder: HtmlScope.() -> Unit): Html {
    val scope = HtmlScope()
    builder(scope)
    return scope.build()
}

private val Language.displayText: String
    get() =
        when (this) {
            Language.Ja -> "ja"
            Language.En -> "en"
            Language.Zh -> "zn"
            Language.Ko -> "ko"
            Language.De -> "de"
            Language.Fr -> "fr"
            Language.It -> "it"
            Language.Ru -> "ru"
        }
