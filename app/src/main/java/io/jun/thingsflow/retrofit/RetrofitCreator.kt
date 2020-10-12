package io.jun.thingsflow.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitCreator {

    companion object {

        private const val baseUrl = "https://api.github.com/"

        fun createRetrofit():Retrofit =
                Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}