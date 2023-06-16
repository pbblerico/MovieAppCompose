package com.example.movieappcompose.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieappcompose.models.Movie
import com.example.movieappcompose.ui.theme.base


@Composable
fun MovieDetail(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(base)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth()
                ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                imageVector = Icons.Rounded.Image,
                contentDescription = "",
                modifier = Modifier
                    .width(200.dp)
                    .height(300.dp)
            )
            Text(
                text = movie.title,
                style = MaterialTheme.typography.h4
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Movie Info:",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(vertical = 5.dp))
            Column{
                Row(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text("rating:")
                    Text(movie.rating.toString(), modifier = Modifier.padding(horizontal = 10.dp))
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text("date release:")
                    Text(movie.releaseDate, modifier = Modifier.padding(horizontal = 10.dp))
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text("language:")
                    Text(movie.language, modifier = Modifier.padding(horizontal = 10.dp))
                }
            }
            Text(text = "Overview:",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(vertical = 5.dp))
            Text("Lorem", modifier = Modifier.padding(horizontal = 30.dp))
        }
    }


}


@Composable
fun MovieDetailPage(movie: Movie = Movie()) {
    Text("heloo")
}
//
@Preview(showBackground = true)
@Composable
fun PreviewDetail() {
    MovieDetailPage()
}