package com.example.huckathon.presentation.screens.paymentscreen.uistate

import com.example.huckathon.domain.models.City
import com.example.huckathon.domain.models.PaymentMethod
import com.example.huckathon.domain.models.TransportOption

data class PaymentScreenUiState(
    val city: City? = null,
    val transportOption: TransportOption? = null,

    val paymentMethods: List<PaymentMethod> = emptyList(),
    val selectedMethodId: String? = null,

    val isPaymentProcessing: Boolean = false,
    val isPaymentSuccessful: Boolean = false,
    val error: String? = null
)