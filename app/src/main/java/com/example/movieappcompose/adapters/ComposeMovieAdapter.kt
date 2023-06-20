package com.example.movieappcompose.adapters

import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappcompose.shared.ui.composables.MovieCard
import com.example.movieappcompose.shared.data.models.ListItem
import com.example.movieappcompose.shared.data.models.Movie

class ComposeMovieAdapter(
    private val onItemClick: (id: Long) -> Unit,
    private val onIconButtonClick: (movie: Movie) -> Unit,
    private val isFavouriteList: Boolean
): PagingDataAdapter<ListItem, ComposeMovieAdapter.ComposeMovieViewHolder>(DiffCallback()){

    class ComposeMovieViewHolder(
        private val composeView: ComposeView
    ): RecyclerView.ViewHolder(composeView) {

        fun bind(item: ListItem,
                 onItemClick: (id: Long) -> Unit,
                 onIconButtonClick: (movie: Movie) -> Unit,
                 isFavouriteList: Boolean) {
            when(item) {
                is ListItem.Movie -> bindMovies(item.movie, onItemClick, onIconButtonClick, isFavouriteList)
                else -> {}
            }
        }
        private fun bindMovies(movie: Movie,
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

    class DiffCallback: DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem) = oldItem == newItem
        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComposeMovieViewHolder {
        return ComposeMovieViewHolder(ComposeView(parent.context))
    }

    override fun onBindViewHolder(holder: ComposeMovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onItemClick, onIconButtonClick, isFavouriteList) }
    }
}