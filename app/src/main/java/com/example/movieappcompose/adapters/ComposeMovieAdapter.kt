package com.example.movieappcompose.adapters

import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappcompose.composables.MovieCard
import com.example.movieappcompose.models.Movie

class ComposeMovieAdapter(
    private val onItemClick: (id: Long) -> Unit,
    private val onIconButtonClick: (movie: Movie) -> Unit,
    private val isFavouriteList: Boolean
): ListAdapter<Movie, ComposeMovieAdapter.ComposeMovieViewHolder>(DiffCallback()){

    class ComposeMovieViewHolder(
        private val composeView: ComposeView
    ): RecyclerView.ViewHolder(composeView) {
        fun bind(movie: Movie,
                 onItemClick: (id: Long) -> Unit,
                 onIconButtonClick: (movie: Movie) -> Unit,
                 isFavouriteList: Boolean) {
            composeView.setContent {
                MovieCard(
                    movie = movie,
                    onItemClick = onItemClick,
                    onIconButtonClick = onIconButtonClick,
                    isFavouriteList = isFavouriteList
                )
            }
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComposeMovieViewHolder {
        return ComposeMovieViewHolder(ComposeView(parent.context))
    }

    override fun onBindViewHolder(holder: ComposeMovieViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick, onIconButtonClick, isFavouriteList)
    }
}