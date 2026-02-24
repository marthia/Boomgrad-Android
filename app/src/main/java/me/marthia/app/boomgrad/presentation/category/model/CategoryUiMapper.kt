package me.marthia.app.boomgrad.presentation.category.model

import android.content.Context
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.model.CategoryType


fun AttractionCategory.toUi(context: Context) = CategoryUi(
    id = id,
    type = type.toUiLabel(context = context),
    description = description,
    image = image,
)

fun CategoryType.toUiLabel(context: Context): String {
    return when (this) {
        CategoryType.CULTURAL -> context.getString(R.string.label_category_type_cultural)
        CategoryType.URBAN -> context.getString(R.string.label_category_type_urban)
        CategoryType.RURAL -> context.getString(R.string.label_category_type_rural)
        CategoryType.RELIGIOUS -> context.getString(R.string.label_category_type_religious)
        CategoryType.ADVENTUROUS -> context.getString(R.string.label_category_type_adventurous)
        CategoryType.BUSINESS -> context.getString(R.string.label_category_type_business)
        CategoryType.HEALTH -> context.getString(R.string.label_category_type_health)
        CategoryType.LITERATURE -> context.getString(R.string.label_category_type_literature)
        CategoryType.CREATIVE -> context.getString(R.string.label_category_type_creative)
        CategoryType.CULINARY -> context.getString(R.string.label_category_type_culinary)
        CategoryType.EDUCATIONAL -> context.getString(R.string.label_category_type_educational)
        CategoryType.WELLNESS -> context.getString(R.string.label_category_type_wellness)
        CategoryType.NATURE -> context.getString(R.string.label_category_type_nature)
        CategoryType.HERITAGE -> context.getString(R.string.label_category_type_heritage)
    }
}