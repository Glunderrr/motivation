package com.example.myapplication.view.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.ui.theme.Paddings
import kotlinx.coroutines.delay

@Composable
fun PhraseCard(
    modifier: Modifier = Modifier,
    phrase: Phrase,
    onLongPress: ((Phrase) -> Unit)? = null,
    onTap: ((Phrase) -> Unit)? = null,
    onDoubleTap: ((Phrase) -> Unit)? = null,
    onCopyClick: ((Phrase) -> Unit)? = null,
    listIsEmpty: Boolean = true,
    selected: Boolean = false,
    onEdit: ((Phrase) -> Unit)? = null,
) {
    val hasGestures = onLongPress != null || onTap != null || onDoubleTap != null

    key(phrase.isLiked) {
        var showLikeAnimation by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .then(
                        if (hasGestures) Modifier.pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = { onLongPress?.invoke(phrase) },
                                onTap = { onTap?.invoke(phrase) },
                                onDoubleTap = onDoubleTap?.let { handler ->
                                    {
                                        handler(phrase)
                                        if (!phrase.isLiked) showLikeAnimation = true
                                    }
                                }
                            )
                        } else Modifier
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = if (selected) colorScheme.primaryContainer
                    else colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = shapes.large,
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
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(Paddings.ExtraSmall.dp)
                            ) {
                                Text(
                                    text = phrase.theme,
                                    style = typography.titleSmall,
                                    color = if (selected) colorScheme.onPrimaryContainer
                                    else colorScheme.primary
                                )
                                if (phrase.isOwn) {
                                    Icon(
                                        imageVector = Icons.Outlined.AccountCircle,
                                        contentDescription = null,
                                        tint = colorScheme.tertiary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                            Text(
                                text = phrase.date,
                                style = typography.labelSmall,
                                color = colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }
                        if (hasGestures && !listIsEmpty) {
                            RadioButton(
                                selected = selected,
                                onClick = { onTap?.invoke(phrase) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = colorScheme.primary,
                                    unselectedColor = colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                )
                            )
                        }
                    }

                    Text(
                        text = phrase.phrase,
                        style = typography.bodyLarge,
                        color = if (selected) colorScheme.onPrimaryContainer
                        else colorScheme.onSurface
                    )

                    if (onDoubleTap != null || onCopyClick != null || (onEdit != null && phrase.isOwn)) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(Paddings.Medium.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (onDoubleTap != null) {
                                Icon(
                                    imageVector = if (phrase.isLiked) Icons.Filled.Favorite
                                    else Icons.Filled.FavoriteBorder,
                                    tint = if (phrase.isLiked) colorScheme.primary
                                    else colorScheme.onSurfaceVariant,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .clickable { onDoubleTap.invoke(phrase) }
                                )
                            }
                            if (onCopyClick != null) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = null,
                                    tint = colorScheme.onSurfaceVariant,
                                    modifier = Modifier
                                        .clip(shapes.small)
                                        .clickable { onCopyClick.invoke(phrase) }
                                        .size(24.dp)
                                )
                            }
                            if (onEdit != null && phrase.isOwn) {
                                Spacer(Modifier.weight(1f))
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = colorScheme.tertiary,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .clickable { onEdit.invoke(phrase) }
                                        .size(24.dp)
                                )
                            }
                        }
                    }
                }
            }

            if (onDoubleTap != null) {
                AnimatedVisibility(
                    visible = showLikeAnimation,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        tint = colorScheme.primary,
                        contentDescription = null,
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
}
