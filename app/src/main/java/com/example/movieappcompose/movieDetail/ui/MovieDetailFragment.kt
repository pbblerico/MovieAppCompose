package com.example.movieappcompose.movieDetail.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.example.movieappcompose.MainActivity
import com.example.movieappcompose.shared.ui.composables.CustomProgressBar
import com.example.movieappcompose.movieDetail.viewModel.MovieDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.movieappcompose.utils.Result

class MovieDetailFragment: Fragment() {
    private val args: MovieDetailFragmentArgs by navArgs()
    private var movieId: Long? = 0
    private val viewModel by viewModel<MovieDetailViewModel>()
   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
       movieId = args.id
       movieId?.let { id ->
           viewModel.getMovieDetail(id)
           viewModel.movieDetailState.observe(viewLifecycleOwner) {
               setContent {
                   when (it) {
                       is Result.Loading -> {
                           CustomProgressBar()
                       }
                       is Result.Success -> {
                           it.data?.let { movie -> CollapsingToolbar(movie) }
                       }
                       is Result.Failure -> {
//                       (requireActivity() as MainActivity).hideProgressBar()
                       }
                       is Result.Empty -> {
//                       (requireActivity() as MainActivity).hideProgressBar()
                       }
                   }
               }
           }
       }
    }
}