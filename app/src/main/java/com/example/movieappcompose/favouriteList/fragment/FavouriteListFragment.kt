package com.example.movieappcompose.favouriteList.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappcompose.MovieListContract
import com.example.movieappcompose.MovieViewModel
import com.example.movieappcompose.adapters.FavouriteMovieAdapter
import com.example.movieappcompose.shared.ui.composables.CustomProgressBar
import com.example.movieappcompose.shared.ui.composables.MovieList
import com.example.movieappcompose.utils.Result
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteListFragment : Fragment() {
    private val viewModel by viewModel<MovieViewModel>()
    private val favouriteListAdapter: FavouriteMovieAdapter by lazy {
        FavouriteMovieAdapter(
            onItemClick = {id -> viewModel.setEvent(MovieListContract.Event.OnMovieClicked(id))},
            onIconButtonClick = {movie -> viewModel.setEvent(MovieListContract.Event.OnIconButtonClicked(movie, true))}
        )
    }
    private val recyclerView: RecyclerView by lazy {
        RecyclerView(requireContext()).apply {
            adapter = favouriteListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        viewModel.getFavoritesList()
//       setContent {
//           val state: Result<List<Movie>> by viewModel.favouritesListStatus
//
//           when(state) {
//               is Result.Loading -> {
////                        setContent {
//                            CustomProgressBar()
////                        }
//                   }
//                   is Result.Success -> {
//                       favouriteListAdapter.submitList(state.data)
////                       setContent {
//                           MovieList(recyclerView = recyclerView)
////                       }
//                   }
//
//                   is Result.Failure -> {
//                       Log.d("favourite", "erre")
//                   }
//
//                   is Result.Empty -> {
//                   }
//           }
//       }

       viewLifecycleOwner.lifecycleScope.launch {
           viewModel.favouritesListStatus.collectLatest {
               when (it) {
                   is Result.Loading -> {
                        setContent {
                            CustomProgressBar()
                        }
                   }
                   is Result.Success -> {
                       favouriteListAdapter.submitList(it.data)
                       setContent {
                           MovieList(recyclerView = recyclerView)
                       }
                   }

                   is Result.Failure -> {
                       Log.d("favourite", "erre")
                   }

                   is Result.Empty -> {
                   }
               }
           }
       }

       viewLifecycleOwner.lifecycleScope.launch {
           viewModel.effect.collectLatest {
               when(it) {
                   is MovieListContract.Effect.NavigateToDetails -> {
                       it.id?.let { id -> onItemClicked(id) }
                   }
                  is MovieListContract.Effect.OnIconButtonClick -> {
                      Log.d("remove", "Here")
                      it.movie?.let { movie -> viewModel.removeFromFavourite(movie.id.toString()) }

                  }
                   is MovieListContract.Effect.Empty -> Unit
               }
           }
       }
   }

    private fun onItemClicked(id: Long) {
        val action = FavouriteListFragmentDirections.favouriteListToMovieDetailFragment(id)
        findNavController().navigate(action)
    }
}