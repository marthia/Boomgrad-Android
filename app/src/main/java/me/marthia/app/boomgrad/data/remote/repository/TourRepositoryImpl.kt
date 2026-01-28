package me.marthia.app.boomgrad.data.remote.repository

import me.marthia.app.boomgrad.data.mapper.toDomain
import me.marthia.app.boomgrad.data.remote.api.TourApiService
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.model.Guide
import me.marthia.app.boomgrad.domain.model.ItineraryStop
import me.marthia.app.boomgrad.domain.model.Tour
import me.marthia.app.boomgrad.domain.model.TourStatus
import me.marthia.app.boomgrad.domain.repository.TourRepository

class TourRepositoryImpl(
    private val api: TourApiService
) : TourRepository {
    override suspend fun getList(): Result<List<Tour>> {
        return try {
//            val response = api.getTours()
//            val tours = response.toDomainList()

            val tour = Tour(
                id = -1,
                title = "میدان نقش جهان",
                description = "با این تور یک روزه، سفری به قلب تاریخ و هنر اصفهان خواهید داشت. میدان نقش جهان، یکی از بزرگترین و زیباترین میادین جهان، میزبان شما خواهد بود. در این گشت، از شاهکارهای معماری دوران صفوی مانند مسجد شیخ لطف‌الله، مسجد امام و کاخ عالی‌قاپو دیدن خواهید کرد. همچنین، فرصت گشت و گذار در بازار قیصریه و خرید صنایع دستی اصیل اصفهان را خواهید داشت. این تور تجربه‌ای فراموش‌نشدنی از فرهنگ غنی ایران را برای شما به ارمغان می‌آورد.",
                guide = Guide(
                    id = 1,
                    bio = "امین عدیلی دانش‌آموخته رشته مهندسی مواد در دانشگاه پلی‌تکنیک لندن اهل کرمانشاه یکی از برجسته‌ترین راهنماهای گردشگری با تخصص تاریخ هخامنشیان است.",
                    fullName = "امین عدیلی",
                    userId = 10,
                    verified = true,
                    totalTours = 46,
                    averageRating = 4.1f,
                ),
                highlights = listOf(
                    "شناخت تاریخ",
                    "نوشیدنی مخصوص",
                    "گز اصفهان",
                    "مسجد و محراب",
                    "چاه حاج میرزا",
                ),
                duration = 108,
                price = 123546.8,
                category = AttractionCategory(
                    id = 0,
                    name = "تاریخی",
                    description = "",
                    image = ""
                ),
                maxPeople = 7,
                status = TourStatus.PENDING,
                rate = 4.8f,
                reviews = listOf(),
                images = listOf(),
                requiredItems = listOf(
                    "کفش کوهنوردی",
                    "کلاه",
                    "عینک آفتابی",
                    "تنقلات",
                    "زیرانداز تک نفره",
                    "پول نقد",
                ),
                level = "آسان",
                dueDate = "۱۶ بهمن",
                startTime = "۸ صبح",
                demographic = "14-65",
                itinerary =
                    listOf(
                        ItineraryStop("میدان نقش جهان", "شنبه ۲۴ دی ساعت ۱۶"),
                        ItineraryStop("سی و سه پل", "ساعت ۱۷"),
                        ItineraryStop("کلیسای وانک", "ساعت ۱۸"),
                        ItineraryStop("کلیسای مریم مقدس", "ساعت ۱۹"),
                        ItineraryStop("میدان جلفا", "شنبه ۲۴ دی ساعت ۲۰"),
                    ),
                city = City(
                    id = 1,
                    name = "اصفهان",
                    county = "مرکزی",
                    province = "اصفهان",
                )
            )
            val tours = mutableListOf<Tour>()
            repeat(5) {
                tours.add(tour.copy(id = it.toLong()))
            }
            Result.success(tours)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTourDetail(tourId: Long): Result<Tour> {
        return try {
            val response = api.getTourById(tourId)
            val tour = response.toDomain()
            Result.success(tour)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}