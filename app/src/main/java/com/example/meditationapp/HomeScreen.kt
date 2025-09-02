package com.example.meditationapp

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.meditationapp.ui.theme.*

// Data classes
data class Feature(
    val title: String,
    val iconId: Int,
    val lightColor: Color,
    val mediumColor: Color,
    val darkColor: Color
)

// Extension function for quadratic bezier curve
fun Path.standardQuadTo(from: Offset, to: Offset) {
    quadraticBezierTo(
        from.x, from.y,
        (from.x + to.x) / 2, (from.y + to.y) / 2
    )
}

private val featureList = listOf(
    Feature("Sleep meditation", R.drawable.ic_headphone, BlueViolet1, BlueViolet2, BlueViolet3),
    Feature("Tips for sleeping", R.drawable.ic_videocam, LightGreen1, LightGreen2, LightGreen3),
    Feature("Night Island", R.drawable.ic_headphone, OrangeYellow1, OrangeYellow2, OrangeYellow3),
    Feature("Calming Sounds", R.drawable.ic_headphone, Beige1, Beige2, Beige3)
)

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomMenu(
                items = listOf(
                    BottomMenuContent("Home", R.drawable.ic_home),
                    BottomMenuContent("Meditate", R.drawable.ic_bubble),
                    BottomMenuContent("Sleep", R.drawable.ic_moon),
                    BottomMenuContent("Music", R.drawable.ic_music),
                    BottomMenuContent("Profile", R.drawable.ic_profile)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DeepBlue)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .background(BlueViolet2)
                .fillMaxSize()
                .padding(paddingValues) // Apply Scaffold padding
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    GreetingSection(name = "Amaan")
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    ChipSection(chips = listOf("Sweet sleep", "Insomnia", "Depression"))
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    CurrentMeditation(onPlayClick = {
                        try {
                            navController.navigate(Screen.Timer.route)
                        } catch (e: Exception) {
                            println("Navigation error: ${e.message}")
                        }
                    })
                }
                item {
                    FeatureSection()
                }
            }
        }
    }
}
@Composable
fun GreetingSection(name: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        Column {
            Text(
                text = "Good Morning, $name",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "We wish you have a good day!",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search",
            tint = Color.Red,
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun ChipSection(chips: List<String>) {
    var selectedChipIndex by remember { mutableIntStateOf(0) }

    LazyRow {
        items(chips.size) { index ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                    .clickable { selectedChipIndex = index }
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (selectedChipIndex == index) ButtonBlue else DeepBlue)
                    .padding(15.dp)
            ) {
                Text(text = chips[index], color = TextWhite)
            }
        }
    }
}

@Composable
fun CurrentMeditation(
    color: Color = LightRed,
    onPlayClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text("Daily Thought", style = MaterialTheme.typography.headlineMedium)
            Text("Meditation • 3-10 min", style = MaterialTheme.typography.bodyMedium)
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Red)
                .padding(10.dp)
                .clickable(onClick = onPlayClick)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun FeatureSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Features",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(10.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .height(500.dp)
        ) {
            items(featureList) { feature ->
                FeatureItem(feature = feature)
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun FeatureItem(feature: Feature) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(feature.darkColor)
    ) {
        val density = LocalDensity.current
        val widthPx = with(density) { maxWidth.toPx() }
        val heightPx = with(density) { maxHeight.toPx() }

        // Simplified paths to stay within bounds
        val mediumColoredPath = Path().apply {
            moveTo(0f, heightPx * 0.3f)
            standardQuadTo(Offset(0f, heightPx * 0.3f), Offset(widthPx * 0.1f, heightPx * 0.35f))
            standardQuadTo(Offset(widthPx * 0.1f, heightPx * 0.35f), Offset(widthPx * 0.4f, heightPx * 0.05f))
            standardQuadTo(Offset(widthPx * 0.4f, heightPx * 0.05f), Offset(widthPx * 0.75f, heightPx))
            lineTo(widthPx, heightPx)
            lineTo(0f, heightPx)
            close()
        }

        val lightColoredPath = Path().apply {
            moveTo(0f, heightPx * 0.35f)
            standardQuadTo(Offset(0f, heightPx * 0.35f), Offset(widthPx * 0.1f, heightPx * 0.4f))
            standardQuadTo(Offset(widthPx * 0.1f, heightPx * 0.4f), Offset(widthPx * 0.3f, heightPx * 0.35f))
            standardQuadTo(Offset(widthPx * 0.3f, heightPx * 0.35f), Offset(widthPx * 0.65f, heightPx))
            lineTo(widthPx, heightPx)
            lineTo(0f, heightPx)
            close()
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawPath(path = mediumColoredPath, color = feature.mediumColor)
            drawPath(path = lightColoredPath, color = feature.lightColor)
        }

        Box(modifier = Modifier.fillMaxSize().padding(15.dp)) {
            Text(
                text = feature.title,
                style = MaterialTheme.typography.headlineMedium,
                lineHeight = 26.sp,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Icon(
                painter = painterResource(id = feature.iconId),
                contentDescription = feature.title,
                tint = Color.White,
                modifier = Modifier.align(Alignment.BottomStart)
            )
            Text(
                text = "Start",
                color = TextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clickable { /* TODO: Add navigation, e.g., navController.navigate("timer/3") */ }
                    .clip(RoundedCornerShape(10.dp))
                    .background(ButtonBlue)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
        }
    }
}

@Composable
fun BottomMenu(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    initialSelectedItemIndex: Int = 0
) {
    var selectedItemIndex by remember { mutableIntStateOf(initialSelectedItemIndex) }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxWidth()
            .background(DeepBlue)
            .padding(16.dp)

    ) {
        items.forEachIndexed { index, item ->
            BottomMenuItem(
                item = item,
                isSelected = index == selectedItemIndex,
                activeHighlightColor = activeHighlightColor,
                activeTextColor = activeTextColor,
                inactiveTextColor = inactiveTextColor
            ) {
                selectedItemIndex = index
            }
        }
    }
}

@Composable
fun BottomMenuItem(
    item: BottomMenuContent,
    isSelected: Boolean,
    activeHighlightColor: Color,
    activeTextColor: Color,
    inactiveTextColor: Color,
    onItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable { onItemClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSelected) activeHighlightColor else Color.Transparent)
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = item.title,
                tint = if (isSelected) activeTextColor else inactiveTextColor,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = item.title,
            color = if (isSelected) activeTextColor else inactiveTextColor
        )
    }
}