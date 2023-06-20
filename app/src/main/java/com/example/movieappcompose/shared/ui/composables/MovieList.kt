package com.example.movieappcompose.shared.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import coil.compose.AsyncImage
import com.example.movieappcompose.shared.data.models.Movie
import com.example.movieappcompose.utils.Constants

@Composable
@Preview(showBackground = true)
fun MoviePreview() {
    Text("Hello")
}

@Composable
fun MovieList(
    recyclerView: RecyclerView,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AndroidView(
            factory = {
                recyclerView
            }
        )
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    onItemClick: (id: Long) -> Unit,
    onIconButtonClick: (movie: Movie) -> Unit,
    isFavouriteList: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(5.dp)
            .clickable { onItemClick.invoke(movie.id) },
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.primary
            ) {

        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            AsyncImage(
                model = Constants.POSTER_BASE_URL + movie.posterPath,
                contentDescription = "",
                modifier = Modifier
                    .width(115.dp)
                    .height(160.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            MovieCardInfo(movie = movie, isFavouriteList, onIconButtonClick)
        }
    }
}

@Composable
fun MovieCardInfo(
    movie: Movie,
    isFavouriteList: Boolean,
    onIconButtonClick: (movie: Movie) -> Unit
) {
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
                onClick = { onIconButtonClick.invoke(movie) },
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

