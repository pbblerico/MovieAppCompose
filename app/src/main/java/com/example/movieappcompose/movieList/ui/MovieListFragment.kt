package com.example.movieappcompose.movieList.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappcompose.MainActivity
import com.example.movieappcompose.MovieListContract
import com.example.movieappcompose.MovieViewModel
import com.example.movieappcompose.adapters.ComposeMovieAdapter
import com.example.movieappcompose.shared.ui.composables.MovieList
import com.example.movieappcompose.shared.ui.composables.CustomProgressBar
import kotlinx.coroutines.flow.collectLatest
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
        setContent {
            MovieList(recyclerView = recyclerView)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                when (it.movieListState) {
                    is MovieListContract.MovieListState.Loading -> {
                        setContent {
                            CustomProgressBar()
                        }
                    }
                    is MovieListContract.MovieListState.Success -> {
                        setContent {
                            MovieList(recyclerView = recyclerView)
                        }
                    }
                    is MovieListContract.MovieListState.Empty -> {
                        Log.d("state empty", "here")
                    }
                    is MovieListContract.MovieListState.Error -> {
                        Log.d("state error", "here")
                    }
                }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieMultipleListPaging.collectLatest {pagingData ->
                movieAdapter.submitData(pagingData)
            }
        }
    }

    private fun onItemClicked(id: Long) {
        val action = MovieListFragmentDirections.movieListToMovieDetail(id)
        findNavController().navigate(action)
    }

}



