package com.chskela.geotracker.tracker.network

import com.chskela.geotracker.tracker.network.SendLocationApi.retrofitService
import com.chskela.geotracker.tracker.utils.Settings.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface SendLocationApiService {
    @Headers("Content-Type: application/json")
    @POST
    fun sendLocation(@Body locations: List<LocationDto>) : Call<LocationDto>
}

object SendLocationApi {
    val retrofitService: SendLocationApiService by lazy {
        retrofit.create(SendLocationApiService::class.java)
    }
}

class RestApiService {
    fun sendLocation(locations: List<LocationDto>, onResult: (LocationDto?) -> Unit){
        val retrofit = retrofitService
        retrofit.sendLocation(locations).enqueue(
            object : Callback<LocationDto> {
                override fun onFailure(call: Call<LocationDto>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<LocationDto>, response: Response<LocationDto>) {
                    onResult(response.body())
                }
            }
        )
    }
}