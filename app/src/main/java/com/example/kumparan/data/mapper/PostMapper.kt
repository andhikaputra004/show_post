package com.example.kumparan.data.mapper

import com.example.kumparan.data.remote.model.Address
import com.example.kumparan.data.remote.model.AddressDTO
import com.example.kumparan.data.remote.model.Company
import com.example.kumparan.data.remote.model.CompanyDTO

fun Address.toDTO(): AddressDTO {
    return AddressDTO(city, street, suite, zipcode)
}

fun Company.toDTO(): CompanyDTO {
    return CompanyDTO(bs, catchPhrase, name)
}