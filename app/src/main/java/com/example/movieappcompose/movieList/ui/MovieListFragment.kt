package com.example.movieappcompose.movieList.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappcompose.MovieListEvent
import com.example.movieappcompose.adapters.ComposeMovieAdapter
import com.example.movieappcompose.composables.MovieList
import com.example.movieappcompose.movieList.viewModel.MovieViewModelMVI
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment() {
    private val viewModel by viewModel<MovieViewModelMVI>()

    private val movieAdapter: ComposeMovieAdapter by lazy {
        ComposeMovieAdapter(
            onItemClick = {id -> viewModel.getMovieDetails(id)},
            onIconButtonClick = {movie -> viewModel.addToFavourite(movie)},
            isFavouriteList = false
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
        setContent {
            MovieList(recyclerView = recyclerView)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.movieEventFlow.collect {event ->
                when(event) {
                    is MovieListEvent.OnMovieClicked -> onItemClicked(event.id)
                    else -> {}
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       lifecycleScope.launch {
           viewModel.movieMultipleListPaging.collectLatest { pagingData ->
                    movieAdapter?.submitData(pagingData)
           }

       }
    }

    private fun onItemClicked(id: Long) {
        val action = MovieListFragmentDirections.movieListToMovieDetail(id)
        Navigation.findNavController(requireView()).navigate(action)
    }

}



