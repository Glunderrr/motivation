package com.example.myapplication.view.screens.bottom.add

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.myapplication.R
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.ui.theme.Paddings
import com.example.myapplication.view.elements.ChooseThemeButton
import com.example.myapplication.view.elements.PhraseCard
import com.example.myapplication.view.elements.ThemeDialog
import com.example.myapplication.view.screens.own.Own
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun Add(
    modifier: Modifier = Modifier,
    uiState: AddUIState,
    onAction: (AddUIAction) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ThemeDialog(showState = uiState.openThemeDialog, onDismissRequest = {
        onAction(AddUIAction.CloseThemeDialog)
    }, themeList = uiState.themes, onClick = { item ->
        onAction(AddUIAction.CloseThemeDialog)
        onAction(AddUIAction.SelectTheme(item.themeName))
    })

    ShowNewPhrase(
        modifier = Modifier, uiState = uiState, onAction = onAction
    )
    ModalNavigationDrawer(
        drawerContent = {
            DrawerContent(
                modifier = Modifier.fillMaxSize(),
                onClickDrawerElementClick = {
                    onAction(AddUIAction.ChangeSelectedDrawerElement(it))
                    scope.launch {
                        drawerState.close()
                    }
                }, onAboutProgram = {
                    onAction(AddUIAction.OnAboutProgramClick)
                }, selectedElement = uiState.selectedDrawerElement
            )
        }, drawerState = drawerState
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Close Icon",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }, content = { paddingValues ->
                AnimatedContent(
                    uiState.selectedDrawerElement,
                ) {
                    if (it == DrawerElement.Generate) {
                        GenerateScreenContent(
                            modifier = Modifier
                                .fillMaxSize(),
                            uiState = uiState,
                            onAction = onAction
                        )
                    }
                    if (it == DrawerElement.CreateOwn) {
                        Own(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            uiState = uiState,
                            onAction = onAction
                        )
                    }
                }
            })
    }
}

@Composable
private fun DrawerContent(
    modifier: Modifier = Modifier,
    onClickDrawerElementClick: (DrawerElement) -> Unit,
    onAboutProgram: () -> Unit,
    selectedElement: DrawerElement
) {
    ModalDrawerSheet(
        drawerShape = RoundedCornerShape(0.dp),
    ) {
        Column(
            modifier = modifier, verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                listOf(
                    DrawerElement.Generate,
                    DrawerElement.CreateOwn
                ).forEach { drawerElement ->
                    Row(
                        Modifier
                            .clickable {
                                onClickDrawerElementClick(drawerElement)
                            }
                            .padding(vertical = Paddings.Small.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        AnimatedVisibility(visible = selectedElement.route == drawerElement.route) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = Paddings.Small.dp)
                                    .size(20.dp)
                            )
                        }
                        Text(
                            text = stringResource(drawerElement.titleId),
                            style = typography.bodyLarge,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(0.9f)
                        )
                    }
                }
            }
            Column {
                HorizontalDivider()
                Row(
                    Modifier
                        .clickable {
                            onAboutProgram()
                        }
                        .padding(vertical = Paddings.Small.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.drawer_about_program),
                        style = typography.bodyLarge,
                        color = colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )
                }
            }
        }
    }
}


@Composable
private fun GenerateScreenContent(
    modifier: Modifier = Modifier, uiState: AddUIState, onAction: (AddUIAction) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                Paddings.ExtraLarge.dp
            )
        ) {
            Text(
                text = stringResource(R.string.drawer_generate),
                style = typography.displayLarge,
                maxLines = 2,
                textAlign = TextAlign.Center
            )
            ChooseThemeButton(
                modifier = Modifier.fillMaxWidth(0.8f),
                onClick = {
                    onAction(AddUIAction.OpenThemeDialog)
                },
                text = uiState.phrase.theme
            )
        }
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
            val size = 200.dp
            LaunchedEffect(uiState.isLoading) {
                if (uiState.isLoading) {
                    delay(1440)
                    onAction(AddUIAction.IsLoading(false))
                    onAction(AddUIAction.OpenPhraseDialog)
                }
            }
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(size),
                    trackColor = colorScheme.background,
                    strokeWidth = size / 2
                )
            } else {
                Button(
                    shape = CircleShape,
                    enabled = uiState.phrase.theme.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        contentColor = colorScheme.onPrimary
                    ),
                    modifier = Modifier.size(size),
                    onClick = {
                        onAction(AddUIAction.GeneratePhrase)
                        onAction(AddUIAction.IsLoading(true))
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = "Generate Phrase",
                        modifier = Modifier.size(size / 2)
                    )
                }
            }
        }
    }

}

@Composable
private fun ShowNewPhrase(
    modifier: Modifier = Modifier, uiState: AddUIState, onAction: (AddUIAction) -> Unit
) {
    var showLikeAnimation by remember {
        mutableStateOf(false)
    }
    if (uiState.openPhraseDialog && uiState.phrase.phrase.isNotBlank() ) {
        Dialog(
            onDismissRequest = {
                onAction(AddUIAction.ClosePhraseDialog)
            }, properties = DialogProperties(
                dismissOnBackPress = true, dismissOnClickOutside = true
            )
        ) {
            Column(
                modifier = modifier
                    .background(
                        color = colorScheme.background,
                        shape = RoundedCornerShape(Paddings.Large.dp)
                    )
                    .padding(Paddings.Small.dp)
                    .pointerInput(uiState.phrase.isLiked) {
                        detectTapGestures(
                            onDoubleTap = {
                                onAction(AddUIAction.ChangeLikedStatusOfNewPhrase(uiState.phrase))
                                showLikeAnimation = !uiState.phrase.isLiked
                            })
                    }, verticalArrangement = Arrangement.spacedBy(Paddings.Small.dp)

            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    PhraseCard(
                        phrase = uiState.phrase
                    )
                    this@Column.AnimatedVisibility(
                        visible = showLikeAnimation,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut(),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            tint = colorScheme.primary,
                            contentDescription = "Like Animation",
                            modifier = Modifier.size(100.dp)
                        )
                        LaunchedEffect(Unit) {
                            delay(500)
                            showLikeAnimation = false
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        Paddings.Small.dp
                    ),
                ) {
                    IconButton(
                        onClick = {
                            onAction(AddUIAction.ClosePhraseDialog)
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "close Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            onAction(AddUIAction.ChangeLikedStatusOfNewPhrase(uiState.phrase))
                        }) {
                        Icon(
                            imageVector = if (uiState.phrase.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            tint = if (uiState.phrase.isLiked) colorScheme.primary else colorScheme.onSurface,
                            contentDescription = "New like Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Button(
                        modifier = Modifier.weight(1f), onClick = {
                            onAction(AddUIAction.SavePhrase)
                            onAction(AddUIAction.ClosePhraseDialog)
                            showLikeAnimation = false
                        }) {
                        Text(
                            text = stringResource(R.string.save),
                        )
                    }
                }
            }
        }
    }
}