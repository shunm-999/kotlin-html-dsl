package org.example

import org.example.html.Language
import org.example.html.html

// TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val html =
        html {
            language = Language.Ja
            head {
                title = "Sample Site"
                meta {
                    charSet = "utf-8"
                    "author" to "Shun"
                    "description" to "This is Sample Site"
                    "keywords" to "html,css,rfc"
                }
            }
            body {
                p {
                    div {
                        "This is Text".html()
                    }
                }
            }
        }
    println(html.displayText)
}
