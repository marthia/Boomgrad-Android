package me.marthia.app.boomgrad.presentation.tour.model

import me.marthia.app.boomgrad.domain.model.CreateItineraryStop
import me.marthia.app.boomgrad.domain.model.CreateTour
import me.marthia.app.boomgrad.domain.usecase.tour.CreateTourUseCase
import me.marthia.app.boomgrad.presentation.tour.create.CreateEditTourUiState
import me.marthia.app.boomgrad.presentation.tour.create.FormField
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

fun CreateEditTourUiState.toDomain(): CreateTour = CreateTour(
    title = title,
    description = description,
    categoryId = selectedCategoryId,
    cityId = selectedCityId,
    durationMinutes = duration.toIntOrNull() ?: 0,
    price = price.toBigDecimalOrNull() ?: BigDecimal.ZERO,
    maxPeople = maxPeople.toIntOrNull() ?: 0,
    meetingPoint = meetingPoint,
    highlights = highlights.toTagList(),
    images = images,
    requiredItems = requiredItems.toTagList(),
    level = selectedLevel,
    dueDate = dueDate ?: LocalDate.MIN,
    startTime = startTime ?: LocalTime.MIDNIGHT,
    demographic = selectedDemographic.ifBlank { null },
    itinerary = itinerary.map {
        CreateItineraryStop(
            title = it.title,
            description = it.description.ifBlank { null },
        )
    },
)

/** Splits a newline/comma-separated text field into a clean tag list. */
fun String.toTagList(): List<String> =
    split("\n", ",").map { it.trim() }.filter { it.isNotBlank() }

fun List<CreateTourUseCase.ValidationError>.toFieldErrorMap(): Map<FormField, String> =
    associate { error ->
        when (error) {
            CreateTourUseCase.ValidationError.TitleBlank,
            CreateTourUseCase.ValidationError.TitleTooLong -> FormField.TITLE to error.toMessage()

            CreateTourUseCase.ValidationError.DescriptionBlank,
            CreateTourUseCase.ValidationError.DescriptionTooLong -> FormField.DESCRIPTION to error.toMessage()

            CreateTourUseCase.ValidationError.CategoryNotSelected -> FormField.CATEGORY to error.toMessage()
            CreateTourUseCase.ValidationError.CityNotSelected -> FormField.CITY to error.toMessage()
            CreateTourUseCase.ValidationError.DurationTooShort -> FormField.DURATION to error.toMessage()
            CreateTourUseCase.ValidationError.PriceNotPositive -> FormField.PRICE to error.toMessage()
            CreateTourUseCase.ValidationError.MaxPeopleTooLow -> FormField.MAX_PEOPLE to error.toMessage()
            CreateTourUseCase.ValidationError.MeetingPointBlank -> FormField.MEETING_POINT to error.toMessage()
            CreateTourUseCase.ValidationError.LevelNotSelected -> FormField.LEVEL to error.toMessage()
            CreateTourUseCase.ValidationError.DueDateNotInFuture,
            CreateTourUseCase.ValidationError.StartTimeNotSet -> FormField.DUE_DATE to error.toMessage()
        }
    }

fun CreateTourUseCase.ValidationError.toMessage(): String = when (this) {
    CreateTourUseCase.ValidationError.TitleBlank -> "عنوان تور الزامی است"
    CreateTourUseCase.ValidationError.TitleTooLong -> "عنوان نباید بیشتر از ۲۵۵ کاراکتر باشد"
    CreateTourUseCase.ValidationError.DescriptionBlank -> "توضیحات الزامی است"
    CreateTourUseCase.ValidationError.DescriptionTooLong -> "توضیحات نباید بیشتر از ۲۰۰۰ کاراکتر باشد"
    CreateTourUseCase.ValidationError.CategoryNotSelected -> "نوع تور را انتخاب کنید"
    CreateTourUseCase.ValidationError.CityNotSelected -> "شهر را انتخاب کنید"
    CreateTourUseCase.ValidationError.DurationTooShort -> "مدت زمان باید حداقل ۳۰ دقیقه باشد"
    CreateTourUseCase.ValidationError.PriceNotPositive -> "قیمت باید بیشتر از صفر باشد"
    CreateTourUseCase.ValidationError.MaxPeopleTooLow -> "ظرفیت باید حداقل ۱ نفر باشد"
    CreateTourUseCase.ValidationError.MeetingPointBlank -> "محل تجمع الزامی است"
    CreateTourUseCase.ValidationError.LevelNotSelected -> "سطح دشواری را انتخاب کنید"
    CreateTourUseCase.ValidationError.DueDateNotInFuture -> "تاریخ باید در آینده باشد"
    CreateTourUseCase.ValidationError.StartTimeNotSet -> "ساعت شروع را وارد کنید"
}