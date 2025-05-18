package com.example.huckathon.presentation.screens.paymentscreen.uistate

data class QRPayScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val qrCode: String? = null,
    val isQrCodeGenerated: Boolean = false,
    val isPaymentSuccessful: Boolean = false,
    val paymentError: String? = null
)
