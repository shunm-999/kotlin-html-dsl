package org.example.html.head

import org.example.html.Display
import org.example.html.IndentScope
import org.example.html.head.component.MetaData

internal data class Head(
    private val indentScope: IndentScope,
    private val title: String,
    private val metaData: Map<String, MetaData>,
) : Display, IndentScope by indentScope {
    override val displayText: String
        get() =
            buildString {
                withIndent {
                    appendOneLineWithIndent("<head>")
                    addIndent {
                        appendOneLineWithIndentIfNotBlank("<title>$title</title>")
                        metaData.values.forEach {
                            appendOneLineWithIndentIfNotBlank(it.displayText)
                        }
                    }
                    appendOneLineWithIndent("</head>")
                }
            }
}

class HeadScope internal constructor(
    private val indentScope: IndentScope,
) : IndentScope by indentScope {
    var title: String = ""
    private val metaData: MutableMap<String, MetaData> = mutableMapOf()

    internal fun build(): Head =
        Head(
            indentScope = this,
            title = title,
            metaData = metaData,
        )

    fun meta(builder: MetaDataScope.() -> Unit) {
        val scope = MetaDataScope()
        builder(scope)
        metaData += scope.metaData
    }
}

class MetaDataScope internal constructor() {
    internal val metaData: MutableMap<String, MetaData> = mutableMapOf()

    var charSet: String?
        get() = metaData["charset"]?.value
        set(value) {
            metaData["charset"] = MetaData.ChartSet(value ?: "")
        }

    infix fun String.to(content: String) {
        metaData[this] = MetaData.Content(this, content)
    }
}
