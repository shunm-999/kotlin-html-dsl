package org.example.html.head

import org.example.html.Display
import org.example.html.IndentScope
import org.example.html.head.component.MetaData

internal data class Head(
    val depth: Int,
    val title: String,
    val metaData: Map<String, MetaData>,
) : Display, IndentScope by IndentScope(depth) {
    override val displayText: String
        get() =
            buildString {
                withIndent {
                    appendLineIfNotBlank("<title>$title</title>")
                    metaData.values.forEach {
                        appendLineIfNotBlank(it.displayText)
                    }
                }
            }
}

class HeadScope internal constructor(private val depth: Int) {
    var title: String = ""
    private val metaData: MutableMap<String, MetaData> = mutableMapOf()

    internal fun build(): Head =
        Head(
            depth = depth,
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
