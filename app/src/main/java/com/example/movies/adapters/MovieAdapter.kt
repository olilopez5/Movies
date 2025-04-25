package com.example.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.data.Movie
import com.example.movies.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter(var items: List<Movie>, val onClick: (Int) -> Unit) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }


    override fun getItemCount(): Int =  items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val movie = items[position]
        holder.render(movie)

        holder.itemView.setOnClickListener {
            onClick(position)
        }

    }
    fun updateItems(items: List<Movie>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(movie: Movie) {
        binding.titleTextView.text = movie.title
        binding.yearTextView.text = movie.year
        Picasso.get().load(movie.poster).into(binding.posterImageView)
    }
}

