package com.example.gamershub.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Game(
    val count: Long? = null,
    val next: String? = null,
    val previous: Any? = null,
    val results: List<GameResult>? = null,
    val seoTitle: String? = null,
    val seoDescription: String? = null,
    val seoKeywords: String? = null,
    val seoH1: String? = null,
    val noindex: Boolean? = null,
    val nofollow: Boolean? = null,
    val description: String? = null,
    val filters: Filters? = null,
    val nofollowCollections: List<String>? = null
) : Serializable

data class Filters(
    val years: List<FiltersYear>? = null
) : Serializable

data class FiltersYear(
    val from: Long? = null,
    val to: Long? = null,
    val filter: String? = null,
    val decade: Long? = null,
    val years: List<YearYear>? = null,
    val nofollow: Boolean? = null,
    val count: Long? = null
) : Serializable

data class YearYear(
    val year: Long? = null,
    val count: Long? = null,
    val nofollow: Boolean? = null
) : Serializable

data class GameResult(
    val id: Long? = null,
    val slug: String? = null,
    val name: String? = null,
    val released: String? = null,
    val tba: Boolean? = null,
    @SerializedName("background_image")
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
) : Serializable

data class AddedByStatus(
    val yet: Long? = null,
    val owned: Long? = null,
    val beaten: Long? = null,
    val toplay: Long? = null,
    val dropped: Long? = null,
    val playing: Long? = null
) : Serializable

data class EsrbRating(
    val id: Long? = null,
    val name: String? = null,
    val slug: String? = null
) : Serializable

data class Genre(
    val id: Long? = null,
    val name: String? = null,
    val slug: String? = null,
    val gamesCount: Long? = null,
    val imageBackground: String? = null,
    val domain: String? = null,
    val language: Language? = null
) : Serializable

enum class Language {
    Eng
}

data class ParentPlatform(
    val platform: EsrbRating? = null
) : Serializable

data class PlatformElement(
    val platform: PlatformPlatform? = null,
    val releasedAt: String? = null,
    val requirementsEn: RequirementsEn? = null,
    val requirementsRu: Any? = null
) : Serializable

data class PlatformPlatform(
    val id: Long? = null,
    val name: String? = null,
    val slug: String? = null,
    val image: Any? = null,
    val yearEnd: Any? = null,
    val yearStart: Long? = null,
    val gamesCount: Long? = null,
    val imageBackground: String? = null
) : Serializable

data class RequirementsEn(
    val minimum: String? = null,
    val recommended: String? = null
) : Serializable

data class Rating(
    val id: Long? = null,
    val title: String? = null,
    val count: Long? = null,
    val percent: Double? = null
) : Serializable

data class ShortScreenshot(
    val id: Long? = null,
    val image: String? = null
) : Serializable

data class Store(
    val id: Long? = null,
    val store: Genre? = null
) : Serializable

