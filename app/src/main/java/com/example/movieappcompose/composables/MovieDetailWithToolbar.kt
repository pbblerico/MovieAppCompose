package com.example.movieappcompose.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.movieappcompose.models.Movie
import com.example.movieappcompose.ui.theme.base
import com.example.movieappcompose.ui.theme.dark
import com.example.movieappcompose.ui.theme.light
import com.example.movieappcompose.utils.Constants

@Preview(showBackground = true)
@Composable
fun PreviewScrollingMovieDetail() {
//    CollapsingToolbar()
    Title()
}

@Composable
fun CollapsingToolbar() {
    val headerHeightDp = LocalConfiguration.current.screenHeightDp.dp
    val toolbarHeightDp = 56.dp

    val scroll: ScrollState = rememberScrollState(1200)

    val headerHeightPx = with(LocalDensity.current) { headerHeightDp.toPx()}
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeightDp.toPx()}

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(scroll, headerHeightPx)
        Body(scroll)
        Toolbar(scroll, headerHeightPx, toolbarHeightPx)
    }
}

@Composable
fun Title(movie: Movie = Movie()) {
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

@Composable
fun Toolbar(scroll: ScrollState, headerHeightPx: Float, toolbarHeight: Float, title: String = "Title") {
    val toolbarBottom by remember {
        mutableStateOf(headerHeightPx - toolbarHeight - 56)
    }

    val showToolbar by remember {
        derivedStateOf { scroll.value >= toolbarBottom }
    }

    AnimatedVisibility(
        visible = showToolbar,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {

        TopAppBar(
            title = {Text(Movie().title, color = Color.White)},
            modifier = Modifier.background(
                brush = Brush.horizontalGradient(
                    listOf(Color(0xff026586), Color(0xff032C45))
                )
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
                        tint = Color.White
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
fun Rating(movie: Movie = Movie()) {
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
                text = Movie().rating.toString(),
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
fun Body(scroll: ScrollState) {
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
            Title()

            Spacer(modifier = Modifier
                .height(1.dp)
                .background(light)
                .fillMaxWidth(0.8f))

            Overview(movieOverview = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")

            Spacer(modifier = Modifier
                .height(1.dp)
                .background(light)
                .fillMaxWidth(0.8f))

            Rating()
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
fun Header(scroll: ScrollState, headerHeightPx: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(light, base),
                    startY = headerHeightPx / 4
                )
            )
            .graphicsLayer {
                translationY = -scroll.value.toFloat() / 2f // Parallax effect
                alpha = (-1f / headerHeightPx) * scroll.value + 1
            }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize(0.8f)
                .background(light)
                .align(Alignment.Center)
        ) {
            Image(
                painter = rememberAsyncImagePainter(Constants.POSTER_BASE_URL + Movie().posterPath),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}

