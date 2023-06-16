package com.example.movieappcompose.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieappcompose.ui.theme.base
import com.example.movieappcompose.ui.theme.light

private val paddingMedium = 16.dp


@Preview(showBackground = true)
@Composable
fun PreviewScrollingMovieDetail() {
//    MovieDetail()
//    ExpandedToolBar()
//    Header()
    CollapsingToolbar()
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
        Title(scroll, headerHeightPx, toolbarHeightPx)
    }
}

@Composable
fun Title(scroll: ScrollState, headerHeightPx: Float, toolbarHeightPx: Float) {
    var titleHeightPx by remember {
        mutableStateOf(0f)
    }
    val titleHeightDp = with(LocalDensity.current) {titleHeightPx.toDp()}

    Text(
        text = "New York",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
//        modifier = Modifier
//                graphicsLayer {
//                    val collapseRange: Float = (headerHeightPx - toolbarHeightPx)
//                    val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)
//
//                    val titleY = lerp(
//                        headerHeightPx.toDp() - titleHeightDp - paddingMedium, // start Y
//                        toolbarHeightPx.dp / 2 - titleHeightDp / 2, // end Y
//                        collapseFraction
//                    )
//
//                    val titleX = lerp(
//                        titlePaddingStart, // start X
//                        titlePaddingEnd, // end X
//                        collapseFraction
//                    )
//
//                    translationY = titleY.toPx()
//                    translationX = titleX.toPx()
//                }
    )
}

@Composable
fun Toolbar(scroll: ScrollState, headerHeightPx: Float, toolbarHeight: Float) {
    val toolbarBottom by remember {
        mutableStateOf(headerHeightPx - toolbarHeight)
    }

    val showToolbar by remember {
        derivedStateOf { scroll.value >= toolbarBottom }
    }

    AnimatedVisibility(
        visible = showToolbar,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {

        TopAppBar(
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
            title = {},
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )
    }
}

@Composable
fun Body(scroll: ScrollState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(scroll)
    ) {
        val configuration = LocalConfiguration.current
        Spacer(modifier = Modifier.height(configuration.screenHeightDp.dp - 40.dp))

        repeat(20) {
            Text(
                text = "Lorem adadasdafasd faergredfkg[erg f;zdfnboaeg[ f bdfjkg[aerjg]odkb[n[n[n [ij[vn[naer g erndf[b",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .background(base)
                    .padding(16.dp)
            )
        }
    }
}
@Composable
fun Header(scroll: ScrollState, headerHeightPx: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                translationY = -scroll.value.toFloat() / 2f // Parallax effect
                alpha = (-1f / headerHeightPx) * scroll.value + 1
            }
    ){
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(light, base),
                        startY = headerHeightPx / 4
                    )
                )
        )
    }
}

