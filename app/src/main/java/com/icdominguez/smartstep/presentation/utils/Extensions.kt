package com.icdominguez.smartstep.presentation.utils

import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.model.Gender

fun Gender.toUiText() : UiText = when(this) {
    Gender.MALE -> UiText.StringResource(R.string.male_gender)
    Gender.FEMALE -> UiText.StringResource(R.string.female_gender)
}