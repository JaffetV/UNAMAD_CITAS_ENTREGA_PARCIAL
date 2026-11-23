package com.example.comunamadsistemacitas.data.remote.network

import com.example.comunamadsistemacitas.data.remote.api.CitasApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Cliente Singleton de Retrofit configurado con OkHttpClient,
 * convertidor JSON (Gson) y manejador de timeouts.
 */
object RetrofitClient {

    private const val BASE_URL = "https://api.unamad.edu.pe/v1/" // URL base de la API institucional

    /** Interceptor de logs para depuración de peticiones HTTP */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /** Cliente HTTP configurado con tiempos de espera de seguridad */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    /** Instancia lazy de Retrofit */
    val apiService: CitasApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CitasApiService::class.java)
    }
}
