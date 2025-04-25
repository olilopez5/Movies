package com.example.movies.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class MovieResponse (

    @SerializedName("Search") val movies: List<Movie>?,
    @SerializedName("totalResults") val totalResults: String?,
    @SerializedName("Response") val response: String

)


data class Movie (

    @SerializedName("imdbID") val id: String ,
    @SerializedName("Title") val title: String ,
    @SerializedName("Year") val year: String ,
    @SerializedName("Poster") val poster: String ,
    @SerializedName("Plot") val plot: String ,
    @SerializedName("Runtime") val runtime: String ,
    @SerializedName("Director") val director: String ,
    @SerializedName("Genre") val genre: String ,
    @SerializedName("Country") val country: String

)


{


}