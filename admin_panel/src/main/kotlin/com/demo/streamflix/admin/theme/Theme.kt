package com.demo.streamflix.admin.theme

import react.ChildrenBuilder
import react.dom.html.ReactHTML.div

fun ChildrenBuilder.ThemeWrapper(content: ChildrenBuilder.() -> Unit) {
    div {
        css {
            customStyles()
            minHeight = 100.vh
        }
        content()
    }
}