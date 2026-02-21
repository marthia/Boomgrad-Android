package me.marthia.app.boomgrad.presentation.cart.model


import me.marthia.app.boomgrad.domain.model.Cart
import me.marthia.app.boomgrad.domain.model.CartItem
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Maps domain Cart/CartItem → CartUiModel/CartItemUiModel.
 * Call from ViewModel; never from Composable directly.
 *
 * @param locale pass Locale("fa", "IR") for Persian UI, Locale.ENGLISH for English.
 */
class CartUiMapper(private val locale: Locale) {

    // ── Service fee rate (5%) ─────────────────────────────────────────────────
    private val serviceFeeRate = 0.05

    fun mapCart(cart: Cart, availabilityWarnings: Map<Long, Int?>): CartUiModel {
        val items = cart.items.map { item ->
            mapCartItem(item, availabilityWarnings[item.scheduleId])
        }

        val subtotal = items.sumOf { it.pricePerPerson * it.peopleCount }
        val serviceFee = (subtotal * serviceFeeRate).toLong()
        val total = subtotal + serviceFee

        return CartUiModel(
            items = items,
            formattedSubtotal = formatPrice(subtotal),
            formattedServiceFee = formatPrice(serviceFee),
            formattedTotal = formatPrice(total),
            totalAmountToman = total,
            isEmpty = items.isEmpty(),
            hasAvailabilityIssues = items.any { it.availabilityWarning != null },
            isCheckoutEnabled = items.isNotEmpty() && items.none { it.availabilityWarning != null },
        )
    }

    fun mapCartItem(item: CartItem, remainingSpots: Int?): CartItemUiModel {
        val totalPrice = item.pricePerPerson * item.peopleCount

        val warning = when {
            remainingSpots == null -> null
            remainingSpots <= 0 -> warningFullyBooked()
            remainingSpots <= 3 -> warningFewSpotsLeft(remainingSpots)
            else -> null
        }

        return CartItemUiModel(
            id = item.id,
            tourId = item.tourId,
            scheduleId = item.scheduleId,
            tourTitle = item.tourTitle,
            tourImageUrl = item.tourImageUrl,
            tourDate = item.tourDate,
            startTime = item.startTime,
            formattedDate = formatDate(item.tourDate),
            formattedTimeRange = formatTimeRange(item.startTime, item.durationMinutes),
            pricePerPerson = item.pricePerPerson,
            formattedPricePerPerson = formatPrice(item.pricePerPerson),
            formattedTotalPrice = formatPrice(totalPrice),
            peopleCount = item.peopleCount,
            maxPeople = item.maxPeople,
            availabilityWarning = warning,
            currentPrice = item.currentPrice,
            priceChanged = item.priceChanged
        )
    }

    // ── Formatting helpers ────────────────────────────────────────────────────

    /**
     * Formats a Toman amount with thousands separator.
     * FA:  "۱۲۰,۰۰۰ تومان"
     * EN:  "120,000 Toman"
     */
    fun formatPrice(amount: Long): String {
        val symbols = DecimalFormatSymbols(locale)
        val formatter = DecimalFormat("#,###", symbols)
        val formatted = formatter.format(amount)
        return if (isPersian()) "$formatted تومان" else "$formatted Toman"
    }

    /**
     * FA: "۲۶ دی ۱۴۰۳"   EN: "Jan 16, 2025"
     */
    private fun formatDate(date: LocalDate): String {
        // For production, use a Solar Hijri calendar library (e.g. time4j or saman)
        // for Persian dates. Here we format with the locale's default calendar.
        val pattern = if (isPersian()) "dd MMMM yyyy" else "MMM d, yyyy"
        return date.format(DateTimeFormatter.ofPattern(pattern, locale))
    }

    /**
     * FA: "۹:۰۰ تا ۱۳:۰۰"    EN: "9:00 AM – 1:00 PM"
     */
    private fun formatTimeRange(startTime: java.time.LocalTime, durationHours: Int): String {
        val endTime = startTime.plusHours(durationHours.toLong())
        return if (isPersian()) {
            val start = startTime.format(DateTimeFormatter.ofPattern("H:mm", locale))
            val end = endTime.format(DateTimeFormatter.ofPattern("H:mm", locale))
            "$start تا $end"
        } else {
            val start = startTime.format(DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH))
            val end = endTime.format(DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH))
            "$start – $end"
        }
    }

    private fun warningFullyBooked() =
        if (isPersian()) "این تور تکمیل ظرفیت شده" else "This tour is fully booked"

    private fun warningFewSpotsLeft(spots: Int) =
        if (isPersian()) "فقط $spots جای خالی مانده" else "Only $spots spots left"

    private fun isPersian() = locale.language == "fa"
}