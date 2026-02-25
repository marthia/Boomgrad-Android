package me.marthia.app.boomgrad.domain.usecase.tour


import me.marthia.app.boomgrad.domain.model.CreateTour
import me.marthia.app.boomgrad.domain.model.TourDetail
import me.marthia.app.boomgrad.domain.repository.TourRepository
import java.math.BigDecimal
import java.time.LocalDate

class CreateTourUseCase(
    private val repository: TourRepository,
) {
    sealed interface ValidationError {
        data object TitleBlank : ValidationError
        data object TitleTooLong : ValidationError
        data object DescriptionBlank : ValidationError
        data object DescriptionTooLong : ValidationError
        data object CategoryNotSelected : ValidationError
        data object CityNotSelected : ValidationError
        data object DurationTooShort : ValidationError             // < 30 min
        data object PriceNotPositive : ValidationError
        data object MaxPeopleTooLow : ValidationError              // < 1
        data object MeetingPointBlank : ValidationError
        data object LevelNotSelected : ValidationError
        data object DueDateNotInFuture : ValidationError
        data object StartTimeNotSet : ValidationError
    }

    sealed interface CreateTourResult {
        data class Success(val tour: TourDetail) : CreateTourResult
        data class ValidationFailure(val errors: List<ValidationError>) : CreateTourResult
        data class NetworkError(val cause: Throwable) : CreateTourResult
    }

    suspend operator fun invoke(request: CreateTour): CreateTourResult {
        val errors = validate(request)
        if (errors.isNotEmpty()) return CreateTourResult.ValidationFailure(errors)

        return repository.createTour(request).fold(
            onSuccess = { CreateTourResult.Success(it) },
            onFailure = { CreateTourResult.NetworkError(it) },
        )
    }

    private fun validate(r: CreateTour): List<ValidationError> = buildList {
        if (r.title.isBlank()) add(ValidationError.TitleBlank)
        if (r.title.length > 255) add(ValidationError.TitleTooLong)
        if (r.description.isBlank()) add(ValidationError.DescriptionBlank)
        if (r.description.length > 2000) add(ValidationError.DescriptionTooLong)
        if (r.categoryId <= 0L) add(ValidationError.CategoryNotSelected)
        if (r.cityId <= 0L) add(ValidationError.CityNotSelected)
        if (r.durationMinutes < 30) add(ValidationError.DurationTooShort)
        if (r.price <= BigDecimal.ZERO) add(ValidationError.PriceNotPositive)
        if (r.maxPeople < 1) add(ValidationError.MaxPeopleTooLow)
        if (r.meetingPoint.isBlank()) add(ValidationError.MeetingPointBlank)
        if (r.level.isBlank()) add(ValidationError.LevelNotSelected)
        if (!r.dueDate.isAfter(LocalDate.now())) add(ValidationError.DueDateNotInFuture)
    }
}