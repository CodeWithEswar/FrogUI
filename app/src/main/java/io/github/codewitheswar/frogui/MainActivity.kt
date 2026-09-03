package io.github.codewitheswar.frogui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.codewitheswar.frogui.ui.branding.FrogUiLogo
import io.github.codewitheswar.frogui.ui.branding.FrogUiLogoVariant
import io.github.codewitheswar.frogui.ui.branding.FrogUiMark
import io.github.codewitheswar.frogui.ui.theme.FrogUITheme
import io.github.codewitheswar.frogui.ui.theme.FrogUiBlack
import io.github.codewitheswar.frogui.ui.theme.FrogUiWhite
import io.github.codewitheswar.frogui.ui.theme.Zinc400
import io.github.codewitheswar.frogui.ui.theme.Zinc500
import io.github.codewitheswar.frogui.ui.theme.Zinc700
import io.github.codewitheswar.frogui.ui.theme.Zinc800
import io.github.codewitheswar.frogui.ui.theme.Zinc900
import io.github.codewitheswar.frogui.ui.theme.Zinc950

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDark by remember { mutableStateOf(true) }
            FrogUITheme(darkTheme = isDark) {
                FrogUiShowcaseScreen(
                    isDarkTheme = isDark,
                    onToggleTheme = { isDark = !isDark }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrogUiShowcaseScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        FrogUiLogo(
                            size = 32.dp,
                            variant = FrogUiLogoVariant.Auto,
                            contentDescription = null
                        )
                        Text(
                            text = "FrogUI",
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 20.sp
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        FrogUiMark(
                            size = 20.dp,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Toggle Theme"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Hero Section
            item {
                HeroBrandCard(isDarkTheme = isDarkTheme)
            }

            // Size Scalability Section (16dp to 96dp)
            item {
                SectionHeader(title = "Scalability Validation", subtitle = "Legible from 16dp status icons to 96dp hero marks")
                Spacer(modifier = Modifier.height(12.dp))
                ScalabilityValidationStrip()
            }

            // Mark Architecture Section (Badge vs Standalone vs Monochrome)
            item {
                SectionHeader(title = "Brand Mark Architecture", subtitle = "Decoupled badge container and geometric mark")
                Spacer(modifier = Modifier.height(12.dp))
                BrandArchitectureCards()
            }

            // Adaptive Launcher Mask Simulation
            item {
                SectionHeader(title = "Adaptive Launcher Masks", subtitle = "Zero-clipping safe zone validation across OEM shapes")
                Spacer(modifier = Modifier.height(12.dp))
                AdaptiveLauncherMaskRow()
            }

            // Brand Specifications Card
            item {
                BrandSpecificationsCard()
            }
        }
    }
}

@Composable
fun HeroBrandCard(isDarkTheme: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FrogUiLogo(
                size = 80.dp,
                variant = FrogUiLogoVariant.Auto,
                contentDescription = "FrogUI"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "FrogUI",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Developer-Focused Android Component Ecosystem",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PillTag(text = "Monochrome")
                PillTag(text = "VectorDrawable")
                PillTag(text = "Adaptive")
                PillTag(text = "Themed Icon")
            }
        }
    }
}

@Composable
fun PillTag(text: String) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SectionHeader(title: String, subtitle: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ScalabilityValidationStrip() {
    val sizes = listOf(
        16.dp to "16dp",
        20.dp to "20dp",
        24.dp to "24dp",
        32.dp to "32dp",
        40.dp to "40dp",
        48.dp to "48dp",
        64.dp to "64dp"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            items(sizes) { (size, label) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FrogUiMark(
                        size = size,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null
                    )
                    Text(
                        text = label,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun BrandArchitectureCards() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Dark Badge
        ArchitectureCard(
            title = "Canonical Badge",
            subtitle = "Dark container",
            modifier = Modifier.weight(1f)
        ) {
            FrogUiLogo(size = 48.dp, variant = FrogUiLogoVariant.Dark)
        }

        // Inverted Badge
        ArchitectureCard(
            title = "Inverted Badge",
            subtitle = "Light container",
            modifier = Modifier.weight(1f)
        ) {
            FrogUiLogo(size = 48.dp, variant = FrogUiLogoVariant.Light)
        }

        // Standalone Mark
        ArchitectureCard(
            title = "Standalone Mark",
            subtitle = "Uncontained",
            modifier = Modifier.weight(1f)
        ) {
            FrogUiMark(size = 48.dp, tint = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun ArchitectureCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 9.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun AdaptiveLauncherMaskRow() {
    val masks = listOf(
        "Circle" to CircleShape,
        "Squircle" to RoundedCornerShape(32),
        "Rounded Sq" to RoundedCornerShape(20)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        masks.forEach { (label, shape) ->
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(shape)
                        .background(FrogUiBlack)
                        .border(1.dp, Zinc700, shape),
                    contentAlignment = Alignment.Center
                ) {
                    // 54dp mark scaled in 108dp canvas = 50% ratio
                    FrogUiMark(
                        size = 40.dp,
                        tint = FrogUiWhite,
                        contentDescription = null
                    )
                }
                Text(
                    text = label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun BrandSpecificationsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Brand Specifications",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            SpecRow(key = "Palette", value = "Zinc #09090B / White #FFFFFF")
            SpecRow(key = "Geometry", value = "Symmetrical Cubic Bézier Vector")
            SpecRow(key = "Safe Zone", value = "66dp safe circle (0px clipping)")
            SpecRow(key = "Themed Icon", value = "Android 13+ Single-fill Tintable")
            SpecRow(key = "Splash Screen", value = "Android 12+ Native SplashScreen")
        }
    }
}

@Composable
fun SpecRow(key: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = key,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(name = "Showcase Screen Dark", showBackground = true)
@Composable
fun FrogUiShowcasePreviewDark() {
    FrogUITheme(darkTheme = true) {
        FrogUiShowcaseScreen(isDarkTheme = true, onToggleTheme = {})
    }
}

@Preview(name = "Showcase Screen Light", showBackground = true)
@Composable
fun FrogUiShowcasePreviewLight() {
    FrogUITheme(darkTheme = false) {
        FrogUiShowcaseScreen(isDarkTheme = false, onToggleTheme = {})
    }
}