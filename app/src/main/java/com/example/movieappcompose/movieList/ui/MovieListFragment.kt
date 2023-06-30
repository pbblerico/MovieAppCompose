package com.example.movieappcompose.movieList.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappcompose.MainActivity
import com.example.movieappcompose.MovieListContract
import com.example.movieappcompose.MovieViewModel
import com.example.movieappcompose.adapters.ComposeMovieAdapter
import com.example.movieappcompose.shared.ui.composables.CustomProgressBar
import com.example.movieappcompose.shared.ui.composables.MovieList
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment() {
    private val viewModel by viewModel<MovieViewModel>()

    private val movieAdapter: ComposeMovieAdapter by lazy {
        ComposeMovieAdapter(
            onItemClick = { id -> viewModel.setEvent(MovieListContract.Event.OnMovieClicked(id)) },
            onIconButtonClick = { movie -> viewModel.setEvent(MovieListContract.Event.OnIconButtonClicked(movie, false))},
        )
    }

    private val recyclerView: RecyclerView by lazy {
        RecyclerView(requireContext()).apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        (requireActivity() as MainActivity).showBottomBar()
        Log.d("OCV", "here")

        viewModel.setEvent(MovieListContract.Event.ShowMovieList)
        setContent {
            val state: MovieListContract.State by viewModel.uiState.collectAsState()

            when(state.movieListState) {
                MovieListContract.MovieListState.Loading -> {
                    CustomProgressBar()
                }
                is MovieListContract.MovieListState.Success -> {
                    val data = (state.movieListState as MovieListContract.MovieListState.Success).pagingData
                    val coroutine = rememberCoroutineScope()
                    coroutine.launch {
                        if (data != null) {
                            movieAdapter.submitData(data)
                        }
                    }
                    MovieList(recyclerView = recyclerView)
                }
                else -> Unit
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.effect.collect{
                when(it) {
                    is MovieListContract.Effect.NavigateToDetails -> {
                        it.id?.let { it1 -> onItemClicked(it1) }
                    }
                    is MovieListContract.Effect.OnIconButtonClick -> {
                        Log.d("like", "liked")
                        it.movie?.let { movie -> viewModel.addToFavourite(movie) }
                    }
                    is MovieListContract.Effect.Empty -> Unit
                }
            }
        }
    }

    private fun onItemClicked(id: Long) {
        val action = MovieListFragmentDirections.movieListToMovieDetail(id)
        findNavController().navigate(action)
    }

}



