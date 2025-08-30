package com.example.myapplication.view.screens.bottom.profile

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.data.model.Personal
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.ui.theme.FontSize
import com.example.myapplication.ui.theme.Paddings
import com.example.myapplication.view.elements.CustomTextField
import com.example.myapplication.view.elements.CustomTopBar
import com.example.myapplication.view.elements.EmptyListTitle
import com.example.myapplication.view.elements.MultiSelectTextField
import com.example.myapplication.view.elements.PhraseCard
import com.example.myapplication.view.elements.SelectTextField
import com.example.myapplication.view.screens.bottom.add.DrawerElement
import kotlin.math.roundToInt

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun Profile(
    modifier: Modifier,
    uiState: ProfUIState,
    personalState: Personal,
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
            item { CustomDivider() }
            item {
                UserEditData(
                    modifier = Modifier.fillMaxSize(),
                    personalState = personalState,
                    onAction = onAction
                )
            }
            item { CustomDivider() }
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
            item { CustomDivider() }
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

@Composable
private fun UserEditData(
    modifier: Modifier = Modifier,
    personalState: Personal,
    onAction: (ProfUIAction) -> Unit
) {
    var showDetails by rememberSaveable { mutableStateOf(false) }
    val weight by animateFloatAsState(
        if (showDetails) 1f else 0.5f
    )
    FlowRow(
        Modifier
            .fillMaxWidth()
            .padding(vertical = Paddings.ExtraLarge.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(Paddings.ExtraLarge.dp),
        itemVerticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(R.string.personal_data),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(weight)
        )
        Button(
            onClick = {
                showDetails = !showDetails
                if (!showDetails) onAction(ProfUIAction.SavePersonalData)
            },
            modifier = Modifier
                .fillMaxWidth(weight)
        ) {
            Text(
                text = if (!showDetails)
                    stringResource(R.string.change_details)
                else
                    stringResource(R.string.save),

                )
        }
    }
    AnimatedVisibility(
        showDetails,
        enter = slideInVertically()
                + expandVertically(expandFrom = Alignment.Top)
                + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically()
                + shrinkVertically(shrinkTowards = Alignment.Top)
                + fadeOut(targetAlpha = 0.3f)
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(Paddings.ExtraLarge.dp),
        ) {
            CustomTextField(
                value = personalState.name,
                modifier = Modifier.fillMaxWidth(),
                onChange = { onAction(ProfUIAction.SetPersonalData(Personal.NAME_KEY, it)) },
                label = stringResource(R.string.enter_your_name),
            )

            SelectTextField(
                modifier = Modifier.fillMaxWidth(),
                value = if (personalState.age != 0)
                    personalState.age.toString()
                else "",
                onAgeSelected = { ProfUIAction.SetPersonalData(Personal.AGE_KEY, it.toInt()) },
                label = stringResource(R.string.enter_your_age),
                dialogLabel = stringResource(R.string.enter_your_age_dialog),
                list = (12..100).map { it.toString() }
            )

            SelectTextField(
                modifier = Modifier.fillMaxWidth(),
                value = personalState.gender,
                onAgeSelected = {
                    onAction(ProfUIAction.SetPersonalData(Personal.GENDER_KEY, it))
                },
                label = stringResource(R.string.enter_your_gender),
                dialogLabel = stringResource(R.string.enter_your_gender_dialog),
                list = Personal.Gender.entries.map { stringResource(it.valueId) }
            )

            SelectTextField(
                modifier = Modifier.fillMaxWidth(),
                value = personalState.region,
                onAgeSelected = { onAction(ProfUIAction.SetPersonalData(Personal.REGION_KEY, it)) },
                label = stringResource(R.string.enter_your_region),
                dialogLabel = stringResource(R.string.enter_your_region_dialog),
                list = Personal.Region.entries.map { stringResource(it.valueId) }
            )

            MultiSelectTextField(
                modifier = Modifier.fillMaxWidth(),
                value = personalState.personality,
                onValueSelected = {
                    onAction(
                        ProfUIAction.SetPersonalData(
                            Personal.PERSONALITY_KEY,
                            it
                        )
                    )
                },
                label = stringResource(R.string.enter_your_personality),
                dialogLabel = stringResource(R.string.enter_your_personality_dialog),
                options = Personal.Personality.entries.map { stringResource(it.valueId) }
            )

            MultiSelectTextField(
                modifier = Modifier.fillMaxWidth(),
                value = personalState.emotionalState,
                onValueSelected = {
                    onAction(
                        ProfUIAction.SetPersonalData(
                            Personal.EMOTIONAL_STATE_KEY,
                            it
                        )
                    )
                },
                label = stringResource(R.string.enter_your_emotional_state),
                dialogLabel = stringResource(R.string.enter_your_emotional_state_dialog),
                options = Personal.EmotionalState.entries.map { stringResource(it.valueId) }
            )

            MultiSelectTextField(
                modifier = Modifier.fillMaxWidth(),
                value = personalState.userValues,
                onValueSelected = {
                    onAction(
                        ProfUIAction.SetPersonalData(
                            Personal.USER_VALUES_KEY,
                            it
                        )
                    )
                },
                label = stringResource(R.string.enter_your_values),
                dialogLabel = stringResource(R.string.enter_your_values_dialog),
                options = Personal.UserValues.entries.map { stringResource(it.valueId) }
            )

            CustomTextField(
                value = personalState.mainGoal,
                modifier = Modifier.fillMaxWidth(),
                onChange = { onAction(ProfUIAction.SetPersonalData(Personal.MAIN_GOAL_KEY, it)) },
                label = stringResource(R.string.enter_your_main_goal),
                supportingText = stringResource(R.string.enter_your_main_goal_description)
            )

            MultiSelectTextField(
                modifier = Modifier.fillMaxWidth(),
                value = personalState.field,
                onValueSelected = {
                    onAction(
                        ProfUIAction.SetPersonalData(
                            Personal.FIELD_KEY,
                            it
                        )
                    )
                },
                label = stringResource(R.string.enter_your_field),
                dialogLabel = stringResource(R.string.enter_your_field_dialog),
                options = Personal.Field.entries.map { stringResource(it.valueId) }
            )

            MultiSelectTextField(
                modifier = Modifier.fillMaxWidth(),
                value = personalState.challenges,
                onValueSelected = {
                    onAction(
                        ProfUIAction.SetPersonalData(
                            Personal.CHALLENGES_KEY,
                            it
                        )
                    )
                },
                label = stringResource(R.string.enter_your_challenges),
                dialogLabel = stringResource(R.string.enter_your_challenges_dialog),
                options = Personal.Challenge.entries.map { stringResource(it.valueId) }
            )

            SelectTextField(
                modifier = Modifier.fillMaxWidth(),
                value = personalState.experienceLevel,
                onAgeSelected = {
                    onAction(
                        ProfUIAction.SetPersonalData(
                            Personal.EXPERIENCE_LEVEL_KEY,
                            it
                        )
                    )
                },
                label = stringResource(R.string.enter_your_experience_level),
                dialogLabel = stringResource(R.string.enter_your_experience_level_dialog),
                list = Personal.ExperienceLevel.entries.map { stringResource(it.valueId) }
            )

            SelectTextField(
                modifier = Modifier.fillMaxWidth(),
                value = personalState.tone,
                onAgeSelected = { onAction(ProfUIAction.SetPersonalData(Personal.TONE_KEY, it)) },
                label = stringResource(R.string.enter_your_tone),
                dialogLabel = stringResource(R.string.enter_your_tone_dialog),
                list = Personal.Tone.entries.map { stringResource(it.valueId) }
            )

            SelectTextField(
                modifier = Modifier.fillMaxWidth(),
                value = personalState.format,
                onAgeSelected = { onAction(ProfUIAction.SetPersonalData(Personal.FORMAT_KEY, it)) },
                label = stringResource(R.string.enter_your_format),
                dialogLabel = stringResource(R.string.enter_your_format_dialog),
                list = Personal.Format.entries.map { stringResource(it.valueId) }
            )

            SelectTextField(
                modifier = Modifier.fillMaxWidth(),
                value = if (personalState.maxLength != 0)
                    personalState.maxLength.toString()
                else "",
                onAgeSelected = {
                    onAction(
                        ProfUIAction.SetPersonalData(
                            Personal.MAX_LENGTH_KEY,
                            it
                        )
                    )
                },
                label = stringResource(R.string.enter_your_max_length),
                dialogLabel = stringResource(R.string.enter_your_max_length_dialog),
                list = (10..60).map { it.toString() }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.enter_address_user),
                    style = MaterialTheme.typography.titleMedium,
                )
                Switch(
                    checked = personalState.addressUser,
                    onCheckedChange = {
                        onAction(ProfUIAction.SetPersonalData(Personal.ADDRESS_USER_KEY, it))
                    }
                )
            }
        }
    }
}

@Composable
private fun CustomDivider() {
    HorizontalDivider(
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.primary
    )
}
