package br.com.cubos.challenge.android.moviescatalog.data.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiRetrofitFactory {

    companion object {
        const val MOVIE_API_BASE_URL = "https://api.themoviedb.org/3"
        private lateinit var INSTANCE: Retrofit

        fun getInstance(): Retrofit {

            if(INSTANCE == null) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(MOVIE_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(rxAdapter)
                    .build()
            }

            return INSTANCE
        }
    }

}
