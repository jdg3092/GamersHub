package com.example.gamershub.model

data class Game(
    val count: Long? = null,
    val next: String? = null,
    val previous: Any? = null,
    val results: List<Result>? = null,
    val seoTitle: String? = null,
    val seoDescription: String? = null,
    val seoKeywords: String? = null,
    val seoH1: String? = null,
    val noindex: Boolean? = null,
    val nofollow: Boolean? = null,
    val description: String? = null,
    val filters: Filters? = null,
    val nofollowCollections: List<String>? = null
)

data class Filters (
    val years: List<FiltersYear>? = null
)

data class FiltersYear (
    val from: Long? = null,
    val to: Long? = null,
    val filter: String? = null,
    val decade: Long? = null,
    val years: List<YearYear>? = null,
    val nofollow: Boolean? = null,
    val count: Long? = null
)

data class YearYear (
    val year: Long? = null,
    val count: Long? = null,
    val nofollow: Boolean? = null
)

data class Result (
    val id: Long? = null,
    val slug: String? = null,
    val name: String? = null,
    val released: String? = null,
    val tba: Boolean? = null,
    val backgroundImage: String? = null,
    val rating: Double? = null,
    val ratingTop: Long? = null,
    val ratings: List<Rating>? = null,
    val ratingsCount: Long? = null,
    val reviewsTextCount: Long? = null,
    val added: Long? = null,
    val addedByStatus: AddedByStatus? = null,
    val metacritic: Long? = null,
    val playtime: Long? = null,
    val suggestionsCount: Long? = null,
    val updated: String? = null,
    val userGame: Any? = null,
    val reviewsCount: Long? = null,
    val saturatedColor: String? = null,
    val dominantColor: String? = null,
    val platforms: List<PlatformElement>? = null,
    val parentPlatforms: List<ParentPlatform>? = null,
    val genres: List<Genre>? = null,
    val stores: List<Store>? = null,
    val clip: Any? = null,
    val tags: List<Genre>? = null,
    val esrbRating: EsrbRating? = null,
    val shortScreenshots: List<ShortScreenshot>? = null
)

data class AddedByStatus (
    val yet: Long? = null,
    val owned: Long? = null,
    val beaten: Long? = null,
    val toplay: Long? = null,
    val dropped: Long? = null,
    val playing: Long? = null
)

data class EsrbRating (
    val id: Long? = null,
    val name: String? = null,
    val slug: String? = null
)

data class Genre (
    val id: Long? = null,
    val name: String? = null,
    val slug: String? = null,
    val gamesCount: Long? = null,
    val imageBackground: String? = null,
    val domain: String? = null,
    val language: Language? = null
)

enum class Language {
    Eng
}

data class ParentPlatform (
    val platform: EsrbRating? = null
)

data class PlatformElement (
    val platform: PlatformPlatform? = null,
    val releasedAt: String? = null,
    val requirementsEn: RequirementsEn? = null,
    val requirementsRu: Any? = null
)

data class PlatformPlatform (
    val id: Long? = null,
    val name: String? = null,
    val slug: String? = null,
    val image: Any? = null,
    val yearEnd: Any? = null,
    val yearStart: Long? = null,
    val gamesCount: Long? = null,
    val imageBackground: String? = null
)

data class RequirementsEn (
    val minimum: String? = null,
    val recommended: String? = null
)

data class Rating (
    val id: Long? = null,
    val title: String? = null,
    val count: Long? = null,
    val percent: Double? = null
)

data class ShortScreenshot (
    val id: Long? = null,
    val image: String? = null
)

data class Store (
    val id: Long? = null,
    val store: Genre? = null
)

