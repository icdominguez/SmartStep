package com.icdominguez.smartstep.domain.model

enum class WeightUnit(val label: String) {
    KILOS("kg"),
    POUNDS("lbs");

    companion object {
        fun fromLabel(label: String): WeightUnit {
            return entries.find { it.label == label } ?: KILOS
        }
    }
}
