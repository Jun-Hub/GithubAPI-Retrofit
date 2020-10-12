package io.jun.thingsflow.retrofit

import io.jun.thingsflow.model.dto.Issue
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitAPI {

    @GET("repos/{org}/{repo}/issues")
    fun fetchIssue(@Path("org") org: String
                    , @Path("repo") repo: String) : Call<List<Issue>>
}