package com.example.huckathon.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransportOption(
    val name: String,
    val iconRes: Int,
    val minutesLeft: String,
    val impact: String,
    val is_payable: Boolean = false,
    val credit: Int = 0
) : Parcelable