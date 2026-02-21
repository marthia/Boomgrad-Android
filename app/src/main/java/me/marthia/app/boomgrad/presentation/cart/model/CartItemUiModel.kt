package me.marthia.app.boomgrad.presentation.cart.model

import java.time.LocalDate
import java.time.LocalTime

/**
 * UI model for a single item in the cart.
 * All display strings are pre-formatted — ViewModel/Mapper handles locale.
 */
data class CartItemUiModel(
    val id: Long,
    val tourId: Long,
    val scheduleId: Long,

    // ── Display ──────────────────────────────────────────────────────────────
    val tourTitle: String,
    val tourImageUrl: String?,

    // ── Schedule ─────────────────────────────────────────────────────────────
    val tourDate: LocalDate,          // raw value — used for business logic
    val startTime: LocalTime,         // raw value
    val formattedDate: String,        // e.g. "۲۶ دی ۱۴۰۳"  /  "Jan 16, 2025"
    val formattedTimeRange: String,   // e.g. "۹:۰۰ تا ۱۳:۰۰"  /  "9:00 AM – 1:00 PM"

    // ── Pricing ───────────────────────────────────────────────────────────────
    val pricePerPerson: Long,         // raw toman — used for total calculation
    val formattedPricePerPerson: String,  // e.g. "۱۲۰,۰۰۰ تومان"  /  "120,000 Toman"
    val formattedTotalPrice: String,      // pricePerPerson × peopleCount, formatted

    // ── People count ─────────────────────────────────────────────────────────
    // Kept as MutableIntState in the ViewModel; this carries the initial value.
    val peopleCount: Int,
    val maxPeople: Int,               // caps the QuantitySelector upper bound

    // ── Availability warning ──────────────────────────────────────────────────
    // null = no warning; non-null = show warning chip on the card
    val availabilityWarning: String?,  // e.g. "فقط ۲ جای خالی مانده"
    val priceChanged: Boolean?,
    val currentPrice: Long?,
)

/**
 * UI model for the full cart screen.
 */
data class CartUiModel(
    val items: List<CartItemUiModel>,

    // ── Order summary (CartOverview) ──────────────────────────────────────────
    val formattedSubtotal: String,      // sum of all items
    val formattedServiceFee: String,    // platform fee
    val formattedTotal: String,         // subtotal + service fee

    // ── Raw total for sending to Zarinpal ────────────────────────────────────
    val totalAmountToman: Long,

    // ── State flags ───────────────────────────────────────────────────────────
    val isEmpty: Boolean,
    val hasAvailabilityIssues: Boolean, // true if any item has availabilityWarning != null
    val isCheckoutEnabled: Boolean,     // false when cart is empty or has blocking issues
)