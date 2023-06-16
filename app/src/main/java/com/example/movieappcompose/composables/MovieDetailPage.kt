package com.example.movieappcompose.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieappcompose.models.Movie

@Composable
fun MovieDetails(movie: Movie = Movie()) {
    Text("he")
}

@Preview(showBackground = true)
@Composable
fun PreviewMD() {
    MovieDetails()
}