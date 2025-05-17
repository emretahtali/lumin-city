package com.example.huckathon.presentation.screens.paymentscreen.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huckathon.R
import com.example.huckathon.domain.models.City
import com.example.huckathon.domain.models.PaymentMethod
import com.example.huckathon.domain.models.TransportOption
import com.example.huckathon.presentation.screens.paymentscreen.uistate.PaymentScreenUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentScreenViewModel(
    handle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableStateFlow(PaymentScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // dummy methods:
        val methods = listOf(
            PaymentMethod("credits",   "Digital Credits", "45 credits",  R.drawable.credit,   true),
            PaymentMethod("crypto",    "Crypto Wallet",   "2.5 ETH",      R.drawable.ic_crypto,   false),
            PaymentMethod("universal", "Universal Pay",   "Connected",    R.drawable.ic_card,     false)
        )
        _uiState.update { it.copy(paymentMethods = methods, selectedMethodId = "credits") }
    }

    fun setTransportDetails(opt: TransportOption, city: City) {
        _uiState.update { it.copy(transportOption = opt, city = city) }
    }

    fun selectPaymentMethod(id: String) {
        _uiState.update { state ->
            val updated = state.paymentMethods.map {
                it.copy(isSelected = it.id == id)
            }
            state.copy(
                paymentMethods = updated,
                selectedMethodId = id
            )
        }
    }

    fun confirmWithQr() {
        // TODO: launch QR flow
    }

    fun processPayment() {
        viewModelScope.launch {
            _uiState.update { it.copy(isPaymentProcessing = true) }
            delay(2000)
            _uiState.update { it.copy(isPaymentSuccessful = true, isPaymentProcessing = false) }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
