package com.example.huckathon.domain.models

import androidx.annotation.DrawableRes

data class TransportOption(
    val name: String,
    @DrawableRes val iconRes: Int,
    val minutesLeft: String,
    val impact: String
)