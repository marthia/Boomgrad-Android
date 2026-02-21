package me.marthia.app.boomgrad.domain.model

data class Cart(
    val items: List<CartItem>,
    val subtotal: Long,
    val serviceFee: Long,
    val total: Long,
    val hasAvailabilityIssues: Boolean,
    val itemCount: Int,
) {
    val isEmpty: Boolean get() = items.isEmpty()
    val isCheckoutEnabled: Boolean get() = !isEmpty && !hasAvailabilityIssues
}