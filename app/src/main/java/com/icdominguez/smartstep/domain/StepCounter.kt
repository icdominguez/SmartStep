package com.icdominguez.smartstep.domain

import kotlinx.coroutines.flow.StateFlow

interface StepCounter {
    val steps: StateFlow<Int>
    fun start()
    fun stop()
}