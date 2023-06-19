package com.example.movieappcompose.adapters

import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappcompose.models.Movie

class ComposeMovieAdapter: RecyclerView.Adapter<ComposeMovieAdapter.ComposeMovieViewHolder>(){

    class ComposeMovieViewHolder(
        val composeView: ComposeView
    ): RecyclerView.ViewHolder(composeView) {
        fun bind(movie: Movie) {
            composeView.setContent {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComposeMovieViewHolder {
        return ComposeMovieViewHolder(ComposeView(parent.context))
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ComposeMovieViewHolder, position: Int) {
//        val currentItem =
    }
}