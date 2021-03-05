package com.mmodi.countries.model

data class RegionalBloc(
    val acronym: String,
    val name: String,
    val otherAcronyms: List<Any>,
    val otherNames: List<Any>
)