package org.example.html.body.component

internal data class Paragraph(
    override val id: HtmlComponentId?,
    override val classList: List<HtmlComponentClass>,
    override val componentList: List<HtmlComponent>,
) : HtmlComponent {
    override val displayText: String
        get() = TODO("Not yet implemented")
}
