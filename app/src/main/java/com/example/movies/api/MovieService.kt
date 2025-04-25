package com.example.movies.api

import com.example.movies.data.Movie
import com.example.movies.data.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query



interface MovieService {

    @GET("/")
    suspend fun findMoviesByName(
        @Query("s") query: String,
        @Query("apikey") apiKey: String = "13b99058"
    ): MovieResponse

    @GET("/")
    suspend fun findMovieById(
        @Query("i") id: String,
        @Query("apikey") apiKey: String = "13b99058"
    ): Movie
}
