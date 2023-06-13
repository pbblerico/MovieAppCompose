package com.example.movieappcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieappcompose.models.Movie

@Composable
@Preview(showBackground = true)
fun MoviePreview() {
    MovieList()
}


@Composable
fun MovieList(movies: List<Movie> = List(100) {Movie()}) {
    LazyColumn{
        items(items = movies) {movie ->
            Movie(movie)
        }
    }
}

@Composable
fun Movie(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.primary
            ) {

        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Image(
                imageVector = Icons.Rounded.Image,
                contentDescription = "",
                modifier = Modifier
                    .width(115.dp)
                    .height(160.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            MovieInfo(movie = movie, false)

        }
    }
}

@Composable
fun MovieInfo(movie: Movie, boolean: Boolean) {
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Text(
            text = movie.title,
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                iconAndText(imageVector = Icons.Rounded.StarRate, text = movie.rating.toString())
                iconAndText(imageVector = Icons.Rounded.Language, text = movie.language)
                iconAndText(imageVector = Icons.Rounded.CalendarToday, text = movie.dateRelease)
            }
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.Bottom)
            ) {
                Icon(
                    imageVector = if (boolean) Icons.Rounded.Favorite else Icons.Rounded.Delete,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun iconAndText(imageVector: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 5.dp)
    ) {
        Icon(
            imageVector = imageVector, ""
        )
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
    }
}

