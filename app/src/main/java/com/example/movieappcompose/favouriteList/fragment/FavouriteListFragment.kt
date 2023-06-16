package com.example.movieappcompose.favouriteList.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.example.movieappcompose.composables.FavouriteMovieList
import com.example.movieappcompose.R

class FavouriteListFragment : Fragment() {
   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent { 
            FavouriteMovieList(onClick = {})
        }
   }
}