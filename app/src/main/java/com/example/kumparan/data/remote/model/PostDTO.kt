package com.example.kumparan.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PostDTO(
    val title: String,
    val body: String,
    val name: String?,
    val company: String?,
    val email : String?,
    val userId: Int,
    val userAddressDTO: AddressDTO?,
    val userCompanyDTO: CompanyDTO?
) : Parcelable

@Parcelize
data class AddressDTO(
    val city: String,
    val street: String,
    val suite: String,
    val zipcode: String
) : Parcelable

@Parcelize
data class CompanyDTO(
    val bs: String,
    val catchPhrase: String,
    val name: String
) : Parcelable
