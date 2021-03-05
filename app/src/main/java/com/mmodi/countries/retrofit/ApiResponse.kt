package com.mmodi.countries.retrofit

interface ApiResponse {
    fun <T> onSuccess(response: T)
    fun <T> onFailure(error: T)
}