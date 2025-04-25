package com.example.movies.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.R
import com.example.movies.adapters.MovieAdapter
import com.example.movies.api.MovieService
import com.example.movies.data.Movie
import com.example.movies.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private var movieList: List<Movie> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = MovieAdapter(movieList) { position ->
            val movie = movieList[position]


            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("imdbID", movie.id)
            }
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
        binding.recyclerView.adapter = adapter

        findMovieByName("Marvel")

        supportActionBar?.title = "Movies"
        supportActionBar?.setHomeButtonEnabled(true)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val menuItem = menu?.findItem(R.id.action_search)
        val searchView = menuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                findMovieByName(query)

                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
        })

        return true
    }

    fun getRetrofit() : MovieService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MovieService::class.java)
    }



    private fun findMovieByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = getRetrofit()
                val response = service.findMoviesByName(query)

                if (response.response == "True") {
                    movieList = response.movies!! // Asumiendo que la respuesta contiene 'Search'
                    CoroutineScope(Dispatchers.Main).launch {
                        adapter.updateItems(movieList)
                        Log.d("PELIS", "Películas encontradas: ${movieList.size}")
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        Log.e("PELIS", "No se encontraron resultados")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ERROR", "Error al obtener las películas: ${e.message}")
            }
        }
    }

}