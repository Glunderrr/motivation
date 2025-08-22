package com.example.myapplication.view.screens.bottom.profile

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.ui.theme.FontSize
import com.example.myapplication.ui.theme.Paddings
import com.example.myapplication.view.elements.CustomTopBar
import com.example.myapplication.view.elements.EmptyListTitle
import com.example.myapplication.view.elements.PhraseCard
import com.example.myapplication.view.screens.bottom.add.DrawerElement

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun Profile(
    modifier: Modifier,
    uiState: ProfUIState,
    onAction: (ProfUIAction) -> Unit
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                showTopAppBar = uiState.selectedPhrases.isNotEmpty(),
                onClear = { onAction(ProfUIAction.ClearPhraseSelectedList) },
                onDelete = { onAction(ProfUIAction.DeleteSelectedPhrase) }
            )
        }
    ) {
        LazyColumn(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Paddings.Large.dp),
            contentPadding = PaddingValues(Paddings.Large.dp)
        ) {
            item {
                OutlinedCard(
                    shape = MaterialTheme.shapes.large,
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(Paddings.Large.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.lord_voldemort),
                                contentScale = ContentScale.Crop,
                                contentDescription = "Profile Icon",
                                modifier = Modifier
                                    .padding(Paddings.Large.dp)
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            )
                            Column {
                                Text(
                                    "Lord Voldemort",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = FontSize.Large.sp,
                                )
                                Text(
                                    text = stringResource(R.string.status) + "dark lord",
                                    fontSize = FontSize.Medium.sp,
                                )
                            }

                        }
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Paddings.Medium.dp)
                ) {
                    Text(
                        stringResource(R.string.themes),
                        fontSize = FontSize.Large.sp,
                    )

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Paddings.Small.dp),
                        verticalArrangement = Arrangement.spacedBy(Paddings.Small.dp),
                        maxItemsInEachRow = 5,
                    ) {
                        uiState.themes.forEach { item ->
                            InputChip(
                                onClick = {
                                    onAction(ProfUIAction.ChangeThemeStatusInSelectedList(item))
                                },
                                selected = uiState.selectedThemes.contains(item),
                                label = { Text(item.themeName) },
                                shape = RoundedCornerShape(corner = CornerSize(Paddings.Small.dp)),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                            )
                        }
                    }
                }
            }
            item {
                HorizontalDivider(
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            stickyHeader {
                val durationMillis = 100
                val isAllPhraseAnimationValue by animateFloatAsState(
                    targetValue = if (uiState.isAllPhrase)
                        1.5f else 1f,
                    animationSpec = tween(
                        durationMillis = durationMillis,
                        delayMillis = 0,
                        easing = { it })
                )
                val isOwnPhraseAnimationValue by animateFloatAsState(
                    targetValue = if (uiState.isAllPhrase) 1f else 1.5f,
                    animationSpec = tween(
                        durationMillis = durationMillis,
                        delayMillis = 0,
                        easing = { it })
                )
                val primaryColor by animateColorAsState(
                    if (uiState.isAllPhrase) MaterialTheme.colorScheme.onPrimary else
                        MaterialTheme.colorScheme.primary,
                    animationSpec = tween(
                        durationMillis = durationMillis,
                        delayMillis = 0,
                        easing = { it })
                )
                val onPrimaryColor by animateColorAsState(
                    if (!uiState.isAllPhrase) MaterialTheme.colorScheme.onPrimary else
                        MaterialTheme.colorScheme.primary,
                    animationSpec = tween(
                        durationMillis = durationMillis,
                        delayMillis = 0,
                        easing = { it })
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(vertical = Paddings.Small.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .border(
                                border = BorderStroke(
                                    2.dp,
                                    MaterialTheme.colorScheme.primary
                                ),
                                shape = RoundedCornerShape(Paddings.Large.dp)
                            )
                    ) {
                        TextButton(
                            onClick = { onAction(ProfUIAction.ChangeIsAllPhrase(true)) },
                            modifier = Modifier.weight(isAllPhraseAnimationValue),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = onPrimaryColor,
                                contentColor = primaryColor,
                            ),
                            shape = RoundedCornerShape(
                                bottomStart = Paddings.Large.dp,
                                topStart = Paddings.Large.dp,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp,
                            )
                        ) {
                            Text(
                                stringResource(R.string.all_phrase),
                                fontSize = FontSize.Large.sp,
                            )
                        }
                        TextButton(
                            onClick = { onAction(ProfUIAction.ChangeIsAllPhrase(false)) },
                            modifier = Modifier.weight(isOwnPhraseAnimationValue),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = primaryColor,
                                contentColor = onPrimaryColor,
                            ),
                            shape = RoundedCornerShape(
                                bottomStart = 0.dp,
                                topStart = 0.dp,
                                topEnd = Paddings.Large.dp,
                                bottomEnd = Paddings.Large.dp,
                            ),
                        ) {
                            Text(
                                stringResource(R.string.own_phrase),
                                fontSize = FontSize.Large.sp,
                            )
                        }
                    }
                }
            }
            item {
                AnimatedContent(
                    targetState = uiState.isAllPhrase
                ) { isAllPhrase ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(Paddings.Large.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (isAllPhrase) {
                            CustomListPage(
                                list = uiState.allPhraseList,
                                upText = stringResource(R.string.all_empty),
                                downText = stringResource(R.string.pass_to_add_screen),
                                navigateToEditPhrase = { drawerElement, phrase ->
                                    onAction(
                                        ProfUIAction.NavigateToAddScreen(
                                            drawerElement = drawerElement,
                                            phrase = phrase
                                        )
                                    )
                                },
                                navigateToAddFromEmptyScreen = {
                                    onAction(
                                        ProfUIAction.NavigateToAddScreen(
                                            drawerElement = DrawerElement.Generate,
                                        )
                                    )
                                },
                                onLongPress = {
                                    onAction(ProfUIAction.LongPressCard(it))
                                },
                                onTap = {
                                    onAction(ProfUIAction.TapCard(it))
                                },
                                onDoubleTap = {
                                    onAction(ProfUIAction.DoubleTapCard(it))
                                },
                                listIsEmpty = uiState.selectedPhrases.isEmpty(),
                                onCopyClick = {
                                    onAction(ProfUIAction.CopyToClipBoard(it))
                                },
                                selected = { phrase ->
                                    uiState.selectedPhrases.map { it.id }.contains(phrase.id)
                                }
                            )
                        } else {
                            CustomListPage(
                                list = uiState.ownList,
                                upText = stringResource(R.string.own_empty),
                                downText = stringResource(R.string.pass_to_create_phrase_screen),
                                navigateToEditPhrase = { drawerElement, phrase ->
                                    onAction(
                                        ProfUIAction.NavigateToAddScreen(
                                            drawerElement = drawerElement,
                                            phrase = phrase
                                        )
                                    )
                                },
                                navigateToAddFromEmptyScreen = {
                                    onAction(
                                        ProfUIAction.NavigateToAddScreen(
                                            drawerElement = DrawerElement.CreateOwn,
                                        )
                                    )
                                },
                                onLongPress = {
                                    onAction(ProfUIAction.LongPressCard(it))
                                },
                                onTap = {
                                    onAction(ProfUIAction.TapCard(it))
                                },
                                onDoubleTap = {
                                    onAction(ProfUIAction.DoubleTapCard(it))
                                },
                                onCopyClick = {
                                    onAction(ProfUIAction.CopyToClipBoard(it))
                                },
                                listIsEmpty = uiState.selectedPhrases.isEmpty(),
                                selected = { phrase ->
                                    uiState.selectedPhrases.map { it.id }.contains(phrase.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CustomListPage(
    list: List<Phrase>,
    upText: String,
    downText: String,
    navigateToAddFromEmptyScreen: () -> Unit,
    navigateToEditPhrase: (DrawerElement, Phrase) -> Unit,
    onLongPress: (Phrase) -> Unit,
    onTap: (Phrase) -> Unit,
    onDoubleTap: (Phrase) -> Unit,
    onCopyClick: (Phrase) -> Unit,
    listIsEmpty: Boolean,
    selected: (Phrase) -> Boolean
) {
    if (list.isEmpty()) {
        EmptyListTitle(
            upText = upText,
            downText = downText,
        ) {
            navigateToAddFromEmptyScreen()
        }
    } else
        list.forEach { phrase ->
            key(
                phrase.id,
            ) {
                PhraseCard(
                    phrase = phrase,
                    onLongPress = onLongPress,
                    onTap = onTap,
                    onDoubleTap = onDoubleTap,
                    listIsEmpty = listIsEmpty,
                    onCopyClick = onCopyClick,
                    selected = selected(phrase),
                    onEdit = { navigateToEditPhrase(DrawerElement.CreateOwn, phrase) }
                )
            }

        }
}