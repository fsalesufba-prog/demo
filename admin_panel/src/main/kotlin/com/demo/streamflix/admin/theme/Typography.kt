package com.demo.streamflix.admin.theme

import csstype.*

object Typography {
    val h1: PropertiesBuilder.() -> Unit = {
        fontSize = 2.5.rem
        fontWeight = FontWeight.bold
        lineHeight = 1.2
        marginBottom = 1.rem
    }
    
    val h2: PropertiesBuilder.() -> Unit = {
        fontSize = 2.rem
        fontWeight = FontWeight.semiBold
        lineHeight = 1.3
        marginBottom = 0.75.rem
    }
    
    val h3: PropertiesBuilder.() -> Unit = {
        fontSize = 1.75.rem
        fontWeight = FontWeight.medium
        lineHeight = 1.4
        marginBottom = 0.5.rem
    }
    
    val body1: PropertiesBuilder.() -> Unit = {
        fontSize = 1.rem
        lineHeight = 1.5
        color = Colors.textSecondary.toColor()
    }
    
    val caption: PropertiesBuilder.() -> Unit = {
        fontSize = 0.875.rem
        color = Colors.textDisabled.toColor()
    }
}