package org.example.html.body.component

import org.example.html.Display

internal interface HtmlComponent : Display {
    val id: HtmlComponentId?

    val classList: List<HtmlComponentClass>

    val componentList: List<HtmlComponent>

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

internal data class HtmlComponentId(val value: String)

internal data class HtmlComponentClass(val value: String)
