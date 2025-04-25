package com.example.movies.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movies.R
import com.example.movies.api.MovieService
import com.example.movies.data.Movie
import com.example.movies.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding
    lateinit var movie: Movie


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getStringExtra("imdbID")!!

    findMovieById(id)
}

        fun loadData() {

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            binding.titleTextView.text = movie.title
            binding.yearTextView.text = movie.year
            binding.plotTextView.text = movie.plot
            binding.runtimeTextView.text = "Runtime: ${movie.runtime}"
            binding.directorTextView.text = "Director: ${movie.director}"
            binding.genreTextView.text = "Genre: ${movie.genre}"
            binding.countryTextView.text = "Country: ${movie.country}"

            Picasso.get().load(movie.poster).into(binding.posterImageView)
        }

        fun getRetrofit(): MovieService {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(MovieService::class.java)
        }


        private fun findMovieById(query: String) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val service = getRetrofit()
                     movie = service.findMovieById(query)

                    CoroutineScope(Dispatchers.Main).launch {
                        loadData()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }



