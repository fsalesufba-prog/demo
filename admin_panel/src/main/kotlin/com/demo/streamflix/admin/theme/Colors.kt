package com.demo.streamflix.admin.theme

import csstype.PropertiesBuilder
import emotion.react.css
import react.PropsWithChildren
import react.css.css

object Colors {
    const val primary = "#1976d2"
    const val primaryDark = "#1565c0"
    const val primaryLight = "#42a5f5"
    
    const val secondary = "#dc004e"
    const val secondaryDark = "#9a0036"
    
    const val background = "#f5f5f5"
    const val surface = "#ffffff"
    const val error = "#d32f2f"
    const val success = "#2e7d32"
    const val warning = "#ed6c02"
    
    const val textPrimary = "rgba(0, 0, 0, 0.87)"
    const val textSecondary = "rgba(0, 0, 0, 0.6)"
    const val textDisabled = "rgba(0, 0, 0, 0.38)"
    
    const val borderLight = "rgba(0, 0, 0, 0.12)"
    const val borderMedium = "rgba(0, 0, 0, 0.23)"
}

fun PropertiesBuilder.customStyles() {
    backgroundColor = Colors.background.toColor()
    color = Colors.textPrimary.toColor()
    fontFamily = "-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, sans-serif"
}

fun PropertiesBuilder.cardStyle() {
    backgroundColor = Colors.surface.toColor()
    borderRadius = 8.px
    boxShadow = "0 2px 4px rgba(0,0,0,0.1)"
    padding = 24.px
}