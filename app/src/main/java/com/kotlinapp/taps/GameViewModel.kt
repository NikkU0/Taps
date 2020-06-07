@file:Suppress("UNREACHABLE_CODE")

package com.kotlinapp.taps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.max
import kotlin.math.min

class GameViewModel : ViewModel() {

    private var score:Long = 0
    private var speed:Long = 1000
    private var pressed = 0
    private var lives = 0
     fun getCurScore() = score
     fun getspeed():Long = speed
    fun  getlives() = lives
    fun updateScore(tap: Boolean) {
        if (tap) {
            //correct tap
            pressed++
            score += (1000 / speed) * 5
            speed = max(speed - pressed * 5, 200)
        } else {
            lives--

        }
    }
}

