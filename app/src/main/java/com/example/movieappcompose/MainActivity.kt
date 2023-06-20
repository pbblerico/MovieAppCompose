package com.example.movieappcompose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movieappcompose.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding?.let { setContentView(it.root) }
    }

}
//
//@Composable
//fun MyApp(modifier: Modifier = Modifier) {
//
//    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
//
//    Surface(modifier) {
//        if (shouldShowOnboarding) {
//            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
//        } else {
//            Greetings()
//        }
//    }
//}
//
//@Composable
//fun OnboardingScreen(
//    onContinueClicked: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//
//    Column(
//        modifier = modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Welcome to the Basics Codelab!")
//        Button(
//            modifier = Modifier.padding(vertical = 24.dp),
//            onClick = onContinueClicked
//        ) {
//            Text("Continue")
//        }
//    }
//}
//
//@Composable
//private fun Greetings(
//    modifier: Modifier = Modifier,
//    names: List<String> = List(1000) { "$it"}
//) {
//    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
//        items(items = names) {name ->
//            Greeting(name = name)
//        }
//    }
//}
//
//@Preview(showBackground = true, widthDp = 320, heightDp = 320)
//@Composable
//fun OnboardingPreview() {
//    MovieAppComposeTheme {
//        OnboardingScreen(onContinueClicked = {})
//    }
//}
//
//@Composable
//private fun Greeting(name: String) {
//
//    var expanded by remember { mutableStateOf(false) }
//
//    Surface(
//        color = MaterialTheme.colors.primary,
//        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(12.dp)
//                .animateContentSize(
//                    animationSpec = spring(
//                        dampingRatio = Spring.DampingRatioMediumBouncy,
//                        stiffness = Spring.StiffnessLow
//                    )
//                )
//        ) {
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(12.dp)
//            ) {
//                Text(text = "Hello, ")
//                Text(
//                    text = name, style = MaterialTheme.typography.h6.copy(
//                        fontWeight = FontWeight.ExtraBold
//                    )
//                )
//                if (expanded) {
//                    Text(
//                        text = ("Composem ipsum color sit lazy, " +
//                                "padding theme elit, sed do bouncy. ").repeat(4),
//                    )
//                }
//            }
//            IconButton(onClick = { expanded = !expanded }) {
//                Icon(
//                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
//                    contentDescription = if (expanded) {
//                        stringResource(R.string.show_less)
//                    } else {
//                        stringResource(R.string.show_more)
//                    }
//                )
//            }
//        }
//    }
//}
//
//@Preview(
//    showBackground = true,
//    widthDp = 320,
//    uiMode = UI_MODE_NIGHT_YES,
//    name = "dark"
//)
//@Composable
//fun DefaultPreview() {
//    MovieAppComposeTheme {
//        Greetings()
//    }
//}
//
//@Preview
//@Composable
//fun MyAppPreview() {
//    MovieAppComposeTheme {
//        MyApp(Modifier.fillMaxSize())
//    }
//}