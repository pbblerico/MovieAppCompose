package com.example.movieappcompose.movieDetail.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.movieappcompose.shared.data.models.Movie
import com.example.movieappcompose.ui.theme.base
import com.example.movieappcompose.ui.theme.dark
import com.example.movieappcompose.ui.theme.light
import com.example.movieappcompose.utils.Constants

@Preview(showBackground = true)
@Composable
fun PreviewScrollingMovieDetail() {
    CollapsingToolbar(Movie())
}

@Composable
fun CollapsingToolbar(movie: Movie) {
    val headerHeightDp = LocalConfiguration.current.screenHeightDp.dp
    val toolbarHeightDp = 56.dp

    val scroll: ScrollState = rememberScrollState(1200)

    val headerHeightPx = with(LocalDensity.current) { headerHeightDp.toPx()}
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeightDp.toPx()}

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(scroll, headerHeightPx, movie.posterPath)
        Body(scroll, movie)
        Toolbar(scroll, headerHeightPx, toolbarHeightPx, movie)
    }
}

@Composable
fun Title(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp), color = base)
            .padding(vertical = 50.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = movie.title,
            modifier = Modifier.padding(bottom = 30.dp),
            style = MaterialTheme.typography.h5
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MovieDetailButtonIcons(action = {}, imageVector = Icons.Default.StarRate, iconLabel = "Rate")
            MovieDetailButtonIcons(action = {}, imageVector = Icons.Default.Bookmark, iconLabel = "Watch Later")
            MovieDetailButtonIcons(action = {}, imageVector = Icons.Default.Share, iconLabel = "Share")
            MovieDetailButtonIcons(action = {}, imageVector = Icons.Default.MoreHoriz, iconLabel = "More")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Toolbar(scroll: ScrollState, headerHeightPx: Float, toolbarHeight: Float, movie: Movie) {
    val toolbarBottom by remember {
        mutableStateOf(headerHeightPx - toolbarHeight - 56)
    }

    val showToolbar by remember {
        derivedStateOf { scroll.value >= toolbarBottom }
    }

    AnimatedContent(
        targetState = movie.title,
           transitionSpec = {
            fadeIn(tween(1000)) with fadeOut(tween(1000))
        }
//        transitionSpec = {
//            EnterTransition.None with ExitTransition.None
//        }
    )
    {

        TopAppBar(
            title = {
                Text(
                    if (showToolbar) movie.title else "",
                    color = if (showToolbar) Color.Black else Color.Transparent,
                    modifier = Modifier.animateEnterExit(
                        enter = fadeIn(
                            tween(1000)),
                        exit = fadeOut(tween(1000))
                    ))},
            modifier = Modifier.background(
                color =  if (showToolbar) Color.White else Color.Transparent
//                brush = Brush.horizontalGradient(
//                    listOf(Color(0xff026586), Color(0xff032C45))
//                )
            ),
            navigationIcon = {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            },
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )
    }
}


@Composable
fun Overview(movieOverview: String) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val isExpandable by remember { derivedStateOf { textLayoutResult?.didOverflowHeight ?: false } }
    var isExpanded by remember { mutableStateOf(false) }
    val isButtonShown by remember { derivedStateOf { isExpandable || isExpanded } }

    Column(modifier = Modifier
        .animateContentSize(animationSpec = tween(100))
        .background(base)
        .padding(32.dp)
        )
    {
        Text(
            text = movieOverview,
            maxLines = if (isExpanded) Int.MAX_VALUE else 5,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = {textLayoutResult = it}
        )

        if(isButtonShown) {
            TextButton(onClick = { isExpanded = !isExpanded}) {
                Text(
                    if (isExpanded) "Collapse" else "Show more",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun Rating(movieRating: Double) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Kinopoisk Ratings",
            color = Color.White,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth())
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .background(light)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = movieRating.toString(),
                style = MaterialTheme.typography.h3,
                color = dark,
                fontWeight = FontWeight.Bold
            )
            Text(text = "16 814 rates", color = base)
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Rate", fontSize = 20.sp, modifier = Modifier.padding(horizontal = 5.dp).padding(vertical = 2.dp))
            }
        }
    }
}

@Composable
fun Body(scroll: ScrollState, movie: Movie) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(scroll)
    ) {
        val configuration = LocalConfiguration.current
        Spacer(modifier = Modifier.height(configuration.screenHeightDp.dp - 30.dp))

        Column(
            modifier = Modifier
                .background(shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp), color = base)
                .padding(bottom = 200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title(movie)

            Spacer(modifier = Modifier
                .height(1.dp)
                .background(light)
                .fillMaxWidth(0.8f))

            Overview(movie.overview)

            Spacer(modifier = Modifier
                .height(1.dp)
                .background(light)
                .fillMaxWidth(0.8f))

            Rating(movie.rating)
        }
    }
}

@Composable
fun MovieDetailButtonIcons(action: () -> Unit, imageVector: ImageVector, iconLabel: String, contentDescription: String = "") {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
            ){
        IconButton(onClick = { action }) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                modifier = Modifier.size(30.dp)
            )
        }
       Text(
           text = iconLabel,
           fontSize = 10.sp
       )
    }

}

@Composable
fun Header(scroll: ScrollState, headerHeightPx: Float, moviePoster: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(light, base),
                    startY = headerHeightPx / 4
                )
            )
            .graphicsLayer {
                translationY = -scroll.value.toFloat() / 10f // Parallax effect
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ){

            AsyncImage(
                model = Constants.POSTER_BASE_URL + moviePoster,
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(0.8f)
            )

    }
}

