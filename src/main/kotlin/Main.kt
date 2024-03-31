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
            }
            body {
            }
        }
    println(html.displayText)
}
