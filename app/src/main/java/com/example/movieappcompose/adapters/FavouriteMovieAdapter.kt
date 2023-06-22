package com.example.movieappcompose.adapters

import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappcompose.shared.data.models.Movie
import com.example.movieappcompose.shared.ui.composables.MovieCard

class FavouriteMovieAdapter(
    private val onItemClick: (id: Long) -> Unit,
    private val onIconButtonClick: (movie: Movie) -> Unit
): ListAdapter<Movie, FavouriteMovieAdapter.FavouritesViewHolder>(DiffCallback()) {
    class FavouritesViewHolder(
        private val composeView: ComposeView
    ): RecyclerView.ViewHolder(composeView) {
        fun bind(movie: Movie,
                 onItemClick: (id: Long) -> Unit,
                 onIconButtonClick: (movie: Movie) -> Unit){
            composeView.setContent {
                MovieCard(movie = movie, onItemClick = onItemClick,  onIconButtonClick = onIconButtonClick, isFavouriteList = true)
            }
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
       return FavouritesViewHolder(ComposeView(parent.context))
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onItemClick, onIconButtonClick)
        }
    }
}