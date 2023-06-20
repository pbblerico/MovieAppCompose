package com.example.movieappcompose.authorization.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.Navigation
import com.example.movieappcompose.shared.ui.composables.LoginPage
import com.example.movieappcompose.R

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            LoginPage(
                login = {Navigation.findNavController(requireView()).navigate(R.id.loginToMovieList)},
                signUp = {})
        }
    }
}