package com.example.huckathon.domain.models

data class PaymentMethod(
    val id: String,
    val label: String,
    val subtitle: String,
    val iconRes: Int,
    val isSelected: Boolean = false
)