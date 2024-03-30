package org.example.html

import org.example.html.body.Body
import org.example.html.head.Head

data class Html internal constructor(
    val language: Language,
    val head: Head,
    val body: Body
) : Display {
    override val displayText: String
        get() {
            return buildString {
                appendLine("<!DOCTYPE html>")
                appendLine("<html lang=\"${language.displayText}\">")
                appendLine(head.displayText)
                appendLine(body.displayText)
                appendLine("</html>")
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
    Ru
}

class HtmlScope internal constructor() : IndentScope by IndentScope(0) {
    private val head = Head(depth = dig())
    private val body = Body(depth = dig())
    var language: Language? = null

    fun build(): Html = Html(
        language = language ?: throw IllegalStateException("Language is not defined"),
        head = head,
        body = body
    )
}

fun html(builder: HtmlScope.() -> Unit): Html {
    val scope = HtmlScope()
    builder(scope)
    return scope.build()
}

private val Language.displayText: String
    get() = when (this) {
        Language.Ja -> "ja"
        Language.En -> "en"
        Language.Zh -> "zn"
        Language.Ko -> "ko"
        Language.De -> "de"
        Language.Fr -> "fr"
        Language.It -> "it"
        Language.Ru -> "ru"
    }
