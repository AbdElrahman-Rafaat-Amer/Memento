package com.abdelrahman.raafat.memento.domain.validator

sealed class ValidationResult {
    object Success : ValidationResult()

    data class Error(
        val messageResId: Int
    ) : ValidationResult()
}
