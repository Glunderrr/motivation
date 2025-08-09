package com.example.myapplication.view.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.ui.theme.Paddings
import kotlinx.coroutines.delay

@Composable
fun PhraseCard(
    modifier: Modifier = Modifier,
    phrase: Phrase,
    onLongPress: (Phrase) -> Unit,
    onTap: (Phrase) -> Unit,
    onDoubleTap: (Phrase) -> Unit,
    onCopyClick: (Phrase) -> Unit,
    listIsEmpty: Boolean,
    selected: Boolean,
    onEdit: (Phrase) -> Unit = {},
) {
    var showLikeAnimation by remember {
        mutableStateOf(false)
    }
    key(phrase.isLiked) {
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            OutlinedCard(
                modifier = modifier
                    .fillMaxWidth()
                    .background(colorScheme.background)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                onLongPress(phrase)
                            },
                            onTap = {
                                onTap(phrase)
                            },
                            onDoubleTap = {
                                onDoubleTap(phrase)
                                if (!phrase.isLiked)
                                    showLikeAnimation = true
                            }
                        )
                    },
                shape = shapes.large, border = BorderStroke(
                    if (selected) 2.dp
                    else 1.dp, if (selected) colorScheme.primary
                    else colorScheme.tertiary
                )
            ) {
                Column(
                    modifier = Modifier.padding(Paddings.Large.dp),
                    verticalArrangement = Arrangement.spacedBy(Paddings.Medium.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(Paddings.Small.dp)
                            ) {
                                Text(
                                    text = phrase.theme,
                                    style = typography.titleMedium,
                                )
                                if (phrase.isOwn) Icon(
                                    Icons.Default.AccountBox,
                                    contentDescription = "Liked",
                                    tint = colorScheme.secondary
                                )
                            }
                            Text(
                                text = phrase.date,
                                style = typography.bodySmall,
                                color = colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                        if (!listIsEmpty) {
                            RadioButton(
                                selected = selected, onClick = {
                                    onTap(phrase)
                                }, colors = RadioButtonDefaults.colors(
                                    selectedColor = colorScheme.primary,
                                    unselectedColor = colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            )
                        }

                    }
                    Text(
                        text = phrase.phrase,
                        style = typography.bodyLarge,
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Paddings.Medium.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = if (phrase.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            tint = if (phrase.isLiked) colorScheme.primary else colorScheme.onSurface,
                            contentDescription = "Like Icon",
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .clickable {
                                    onDoubleTap(phrase)
                                }
                        )

                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Icon",
                            modifier = Modifier
                                .clip(shapes.small)
                                .clickable { onCopyClick(phrase) }
                                .size(24.dp)
                        )

                        if (phrase.isOwn) {
                            Spacer(modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit phrase Icon",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable { onEdit(phrase) }
                                    .size(30.dp)
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = showLikeAnimation,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut(),
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    tint = colorScheme.primary,
                    contentDescription = "Like Animation",
                    modifier = Modifier.size(75.dp)
                )

                LaunchedEffect(Unit) {
                    delay(500)
                    showLikeAnimation = false
                }
            }
        }
    }
}

@Composable
fun PhraseCard(
    modifier: Modifier = Modifier,
    phrase: Phrase,
    onLongPress: (Phrase) -> Unit,
    onTap: (Phrase) -> Unit,
    onCopyClick: (Phrase) -> Unit,
    listIsEmpty: Boolean,
    selected: Boolean,
    onEdit: (Phrase) -> Unit = {},
) {
    key(
        phrase.isLiked
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            OutlinedCard(
                modifier = modifier
                    .fillMaxWidth()
                    .background(colorScheme.background)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                onLongPress(phrase)
                            },
                            onTap = {
                                onTap(phrase)
                            },
                        )
                    },
                shape = shapes.large, border = BorderStroke(
                    if (selected) 2.dp
                    else 1.dp, if (selected) colorScheme.primary
                    else colorScheme.tertiary
                )
            ) {
                Column(
                    modifier = Modifier.padding(Paddings.Large.dp),
                    verticalArrangement = Arrangement.spacedBy(Paddings.Medium.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(Paddings.Small.dp)
                            ) {
                                Text(
                                    text = phrase.theme,
                                    style = typography.titleMedium,
                                )
                                if (phrase.isOwn) Icon(
                                    Icons.Default.AccountBox,
                                    contentDescription = "Liked",
                                    tint = colorScheme.secondary
                                )
                            }
                            Text(
                                text = phrase.date,
                                style = typography.bodySmall,
                                color = colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                        if (!listIsEmpty) {
                            RadioButton(
                                selected = selected, onClick = {
                                    onTap(phrase)
                                }, colors = RadioButtonDefaults.colors(
                                    selectedColor = colorScheme.primary,
                                    unselectedColor = colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            )
                        }

                    }
                    Text(
                        text = phrase.phrase,
                        style = typography.bodyLarge,
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Paddings.Medium.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "like Icon",
                            modifier = Modifier
                                .clip(shapes.small)
                                .clickable { onCopyClick(phrase) }
                                .size(24.dp)
                        )
                        if (phrase.isOwn) {
                            Spacer(modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Copy All",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable { onEdit(phrase) }
                                    .size(30.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}


@Composable
fun PhraseCard(
    modifier: Modifier = Modifier,
    phrase: Phrase,
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, colorScheme.tertiary),
        shape = shapes.large,
    ) {
        Column(
            modifier = Modifier.padding(Paddings.Large.dp),
            verticalArrangement = Arrangement.spacedBy(Paddings.Medium.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Column {
                Text(
                    text = phrase.theme,
                    style = typography.titleMedium,
                )
                Text(
                    text = phrase.date,
                    style = typography.bodySmall,
                    color = colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Text(
                text = phrase.phrase,
                style = typography.bodyLarge,
            )
        }
    }
}