package com.icdominguez.smartstep.domain.model

enum class HeightUnit(val label: String) {
    CENTIMETERS("cm"),
    FEET("ft/in");

    companion object {
        fun fromLabel(label: String): HeightUnit {
            return entries.find { it.label == label } ?: CENTIMETERS
        }
    }
}