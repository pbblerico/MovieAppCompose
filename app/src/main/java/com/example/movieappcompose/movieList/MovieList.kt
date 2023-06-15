package com.example.movieappcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieappcompose.models.Movie
import com.example.movieappcompose.movieList.viewModel.MovieViewModel
import androidx.compose.runtime.*

@Composable
@Preview(showBackground = true)
fun MoviePreview() {
//    MovieList()
}

@Composable
fun MainMovieList(onClick: (id: String) -> Unit) {
    val viewModel = viewModel<MovieViewModel>()

    val movies = viewModel.movieList()

    MovieList(onClick = onClick, isFavouriteList = false)
}

@Composable
fun FavouriteMovieList(onClick: (id: String) -> Unit) {
    MovieList(onClick = onClick, isFavouriteList = true)
}

@Composable
fun MovieList(
    onClick: (id: String) -> Unit,
    isFavouriteList: Boolean
) {

    val movies = List(100) {it}
    LazyColumn{
        items(items = movies) {movie ->
//            MovieCard(movie, onClick, isFavouriteList)
        }
    }
}

@Composable
fun MovieCard(movie: Movie, onClick: (id: String) -> Unit, isFavouriteList: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(5.dp)
            .clickable { onClick.invoke(movie.title) },
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
            MovieCardInfo(movie = movie, isFavouriteList, onClick)
        }
    }
}

@Composable
fun MovieCardInfo(movie: Movie, isFavouriteList: Boolean, onClick: (id: String) -> Unit) {
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
                CustomIconText(imageVector = Icons.Rounded.StarRate, text = movie.rating.toString())
                CustomIconText(imageVector = Icons.Rounded.Language, text = movie.language)
                CustomIconText(imageVector = Icons.Rounded.CalendarToday, text = movie.releaseDate)
            }
            IconButton(
                onClick = { onClick.invoke(movie.title) },
                modifier = Modifier.align(Alignment.Bottom)
            ) {
                Icon(
                    imageVector = if (isFavouriteList) Icons.Rounded.Delete else Icons.Rounded.Favorite,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Red
                )
            }
        }
    }
}


@Composable
fun CustomIconText(imageVector: ImageVector, text: String) {
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

