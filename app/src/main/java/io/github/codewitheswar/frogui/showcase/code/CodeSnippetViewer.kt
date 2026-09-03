package io.github.codewitheswar.frogui.showcase.code

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.foundation.color.FrogPalette
import io.github.codewitheswar.frogui.foundation.theme.FrogTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Clean developer-tool code snippet block.
 * Monospace typography, horizontal scrolling, subtle container, and interactive copy confirmation.
 */
@Composable
fun CodeSnippetViewer(
    code: String,
    modifier: Modifier = Modifier,
    language: String = "kotlin"
) {
    val clipboardManager = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()
    var isCopied by remember { mutableStateOf(false) }

    val shape = FrogTheme.shapes.md
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(FrogPalette.Zinc950)
            .border(1.dp, FrogPalette.Zinc800, shape)
    ) {
        // Snippet Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(FrogPalette.Zinc900)
                .padding(horizontal = FrogTheme.spacing.md, vertical = FrogTheme.spacing.xs),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = language.uppercase(),
                style = FrogTheme.typography.caption,
                color = FrogPalette.Zinc400
            )

            IconButton(
                onClick = {
                    clipboardManager.setText(AnnotatedString(code))
                    isCopied = true
                    coroutineScope.launch {
                        delay(2000)
                        isCopied = false
                    }
                },
                modifier = Modifier.size(32.dp)
            ) {
                AnimatedContent(
                    targetState = isCopied,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "copy_state"
                ) { copied ->
                    if (copied) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "Copied",
                            tint = FrogPalette.Success,
                            modifier = Modifier.size(16.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.ContentCopy,
                            contentDescription = "Copy code",
                            tint = FrogPalette.Zinc400,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // Code Content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(FrogTheme.spacing.md)
        ) {
            Text(
                text = code,
                style = FrogTheme.typography.code,
                color = FrogPalette.Zinc100
            )
        }
    }
}
