package com.example.kumparan.data.remote.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "address")
    val address: Address,
    @Json(name = "company")
    val company: Company,
    @Json(name = "email")
    val email: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "username")
    val username: String,
    @Json(name = "website")
    val website: String
)

@JsonClass(generateAdapter = true)
data class Address(
    @Json(name = "city")
    val city: String,
    @Json(name = "geo")
    val geo: Geo,
    @Json(name = "street")
    val street: String,
    @Json(name = "suite")
    val suite: String,
    @Json(name = "zipcode")
    val zipcode: String
)

@JsonClass(generateAdapter = true)
data class Company(
    @Json(name = "bs")
    val bs: String,
    @Json(name = "catchPhrase")
    val catchPhrase: String,
    @Json(name = "name")
    val name: String
)

@JsonClass(generateAdapter = true)
data class Geo(
    @Json(name = "lat")
    val lat: String,
    @Json(name = "lng")
    val lng: String
)
