package com.example.myapplication.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.data.model.Personal
import com.example.myapplication.ui.theme.Paddings
import com.example.myapplication.view.elements.CustomTextField
import com.example.myapplication.view.elements.MultiSelectTextField
import com.example.myapplication.view.elements.NextButton
import com.example.myapplication.view.elements.SelectTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    personalState: State<Personal>,
    onChangeByKey: (key: String, value: Any?) -> Unit,
    onSave: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 5 }, initialPage = 0)
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            if (pagerState.currentPage in 1..3) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors().copy(
                        containerColor = MaterialTheme.colorScheme.background,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    actions = {
                        Spacer(modifier = Modifier.width(30.dp))
                    },
                    title = {
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(pagerState.pageCount - 2) { iteration ->
                                val color =
                                    if (pagerState.currentPage == iteration + 1) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.inversePrimary
                                Box(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .weight(1f)
                                        .height(10.dp)
                                        .clip(CircleShape)
                                        .background(color)

                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            modifier = Modifier.size(30.dp),
                            onClick = {
                                scope.launch {
                                    if (pagerState.currentPage > 0) {
                                        pagerState.scrollToPage(pagerState.currentPage - 1)
                                    }
                                }
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back icon",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                )
            }
        }
    ) {
        HorizontalPager(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.background),
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> StartScreen(modifier = modifier) {
                    scope.launch { pagerState.scrollToPage(1) }
                }

                1 -> FirstScreen(
                    modifier = modifier,
                    personalState = personalState,
                    onChangeByKey = onChangeByKey,
                ) {
                    scope.launch { pagerState.scrollToPage(2) }
                }

                2 -> SecondScreen(
                    modifier = modifier,
                    personalState = personalState,
                    onChangeByKey = onChangeByKey
                ) {
                    scope.launch { pagerState.scrollToPage(3) }
                }

                3 -> ThirdScreen(
                    modifier = modifier,
                    personalState = personalState,
                    onChangeByKey = onChangeByKey
                ) {
                    scope.launch { pagerState.scrollToPage(4) }
                }

                4 -> LastScreen(
                    modifier = modifier,
                    onSave = onSave
                )
            }
        }
    }
}

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DisplayText(stringResource(R.string.sign_up_start_screen))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Paddings.Small.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.mascot_motivation),
                contentDescription = null,
                modifier = Modifier
                    .size(350.dp)
            )
            NextButton(
                modifier = Modifier.fillMaxWidth(0.9f),
                onClick = onButtonClick
            )
        }
    }
}

@Composable
fun FirstScreen(
    modifier: Modifier = Modifier,
    personalState: State<Personal>,
    onChangeByKey: (String, Any?) -> Unit,
    nextButtonClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Paddings.ExtraLarge.dp),
        contentPadding = PaddingValues(vertical = Paddings.ExtraLarge.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Paddings.ExtraLarge.dp),
                contentAlignment = Alignment.Center
            ) {
                DisplayText(
                    stringResource(R.string.sign_up_first_screen),
                    textAlign = TextAlign.Left
                )
            }
        }
        item {
            CustomTextField(
                value = personalState.value.name,
                modifier = Modifier.fillMaxWidth(0.9f),
                onChange = { string -> onChangeByKey(Personal.NAME_KEY, string) },
                label = stringResource(R.string.enter_your_name),
            )
        }
        item {
            SelectTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = if (personalState.value.age != 0)
                    personalState.value.age.toString()
                else "",
                onAgeSelected = { onChangeByKey(Personal.AGE_KEY, it) },
                label = stringResource(R.string.enter_your_age),
                dialogLabel = stringResource(R.string.enter_your_age_dialog),
                list = (12..100).map { it.toString() }
            )
        }
        item {
            SelectTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = personalState.value.gender,
                onAgeSelected = { onChangeByKey(Personal.GENDER_KEY, it) },
                label = stringResource(R.string.enter_your_gender),
                dialogLabel = stringResource(R.string.enter_your_gender_dialog),
                list = Personal.Gender.entries.map { stringResource(it.valueId) }
            )
        }

        item {
            SelectTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = personalState.value.region,
                onAgeSelected = { onChangeByKey(Personal.REGION_KEY, it) },
                label = stringResource(R.string.enter_your_region),
                dialogLabel = stringResource(R.string.enter_your_region_dialog),
                list = Personal.Region.entries.map { stringResource(it.valueId) }
            )
        }
        item {
            NextButton(
                modifier = Modifier.fillMaxWidth(0.9f),
                onClick = nextButtonClick,
                enabled = with(personalState.value) {
                    name.isNotEmpty() && age != 0 && gender.isNotEmpty() && region.isNotEmpty()
                }
            )
        }
    }

}

@Composable
fun SecondScreen(
    modifier: Modifier = Modifier,
    personalState: State<Personal>,
    onChangeByKey: (String, Any?) -> Unit,
    nextButtonClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Paddings.ExtraLarge.dp),
        contentPadding = PaddingValues(vertical = Paddings.ExtraLarge.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Paddings.ExtraLarge.dp),
                contentAlignment = Alignment.Center
            ) {
                DisplayText(
                    stringResource(R.string.sign_up_second_screen),
                    textAlign = TextAlign.Left
                )
            }
        }
        item {
            MultiSelectTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = personalState.value.personality,
                onValueSelected = { onChangeByKey(Personal.PERSONALITY_KEY, it) },
                label = stringResource(R.string.enter_your_personality),
                dialogLabel = stringResource(R.string.enter_your_personality_dialog),
                options = Personal.Personality.entries.map { stringResource(it.valueId) }
            )
        }
        item {
            MultiSelectTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = personalState.value.emotionalState,
                onValueSelected = { onChangeByKey(Personal.EMOTIONAL_STATE_KEY, it) },
                label = stringResource(R.string.enter_your_emotional_state),
                dialogLabel = stringResource(R.string.enter_your_emotional_state_dialog),
                options = Personal.EmotionalState.entries.map { stringResource(it.valueId) }
            )

        }
        item {
            MultiSelectTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = personalState.value.userValues,
                onValueSelected = { onChangeByKey(Personal.USER_VALUES_KEY, it) },
                label = stringResource(R.string.enter_your_values),
                dialogLabel = stringResource(R.string.enter_your_values_dialog),
                options = Personal.UserValues.entries.map { stringResource(it.valueId) }
            )
        }

        item {
            CustomTextField(
                value = personalState.value.mainGoal,
                modifier = Modifier.fillMaxWidth(0.9f),
                onChange = { string -> onChangeByKey(Personal.MAIN_GOAL_KEY, string) },
                label = stringResource(R.string.enter_your_main_goal),
                supportingText = stringResource(R.string.enter_your_main_goal_description)
            )
        }
        item {
            MultiSelectTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = personalState.value.field,
                onValueSelected = { onChangeByKey(Personal.FIELD_KEY, it) },
                label = stringResource(R.string.enter_your_field),
                dialogLabel = stringResource(R.string.enter_your_field_dialog),
                options = Personal.Field.entries.map { stringResource(it.valueId) }
            )
        }
        item {
            MultiSelectTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = personalState.value.challenges,
                onValueSelected = { onChangeByKey(Personal.CHALLENGES_KEY, it) },
                label = stringResource(R.string.enter_your_challenges),
                dialogLabel = stringResource(R.string.enter_your_challenges_dialog),
                options = Personal.Challenge.entries.map { stringResource(it.valueId) }
            )
        }
        item {
            SelectTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = personalState.value.experienceLevel,
                onAgeSelected = { onChangeByKey(Personal.EXPERIENCE_LEVEL_KEY, it) },
                label = stringResource(R.string.enter_your_experience_level),
                dialogLabel = stringResource(R.string.enter_your_experience_level_dialog),
                list = Personal.ExperienceLevel.entries.map { stringResource(it.valueId) }
            )
        }
        item {
            NextButton(
                modifier = Modifier.fillMaxWidth(0.9f),
                onClick = nextButtonClick,
                enabled = with(personalState.value) {
                    personality.isNotEmpty() && emotionalState.isNotEmpty() &&
                            userValues.isNotEmpty() && mainGoal.isNotEmpty() &&
                            field.isNotEmpty() && challenges.isNotEmpty() &&
                            experienceLevel.isNotEmpty()
                }
            )
        }
    }
}

@Composable
fun ThirdScreen(
    modifier: Modifier = Modifier,
    personalState: State<Personal>,
    onChangeByKey: (String, Any?) -> Unit,
    nextButtonClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Paddings.ExtraLarge.dp),
        contentPadding = PaddingValues(vertical = Paddings.ExtraLarge.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Paddings.ExtraLarge.dp),
                contentAlignment = Alignment.Center
            ) {
                DisplayText(
                    stringResource(R.string.sign_up_third_screen),
                    textAlign = TextAlign.Right
                )
            }
        }
        item {
            SelectTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = personalState.value.tone,
                onAgeSelected = { onChangeByKey(Personal.TONE_KEY, it) },
                label = stringResource(R.string.enter_your_tone),
                dialogLabel = stringResource(R.string.enter_your_tone_dialog),
                list = Personal.Tone.entries.map { stringResource(it.valueId) }
            )
        }

        item {
            SelectTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = personalState.value.format,
                onAgeSelected = { onChangeByKey(Personal.FORMAT_KEY, it) },
                label = stringResource(R.string.enter_your_format),
                dialogLabel = stringResource(R.string.enter_your_format_dialog),
                list = Personal.Format.entries.map { stringResource(it.valueId) }
            )
        }
        item {
            SelectTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = if (personalState.value.maxLength != 0)
                    personalState.value.maxLength.toString()
                else "",
                onAgeSelected = { onChangeByKey(Personal.MAX_LENGTH_KEY, it) },
                label = stringResource(R.string.enter_your_max_length),
                dialogLabel = stringResource(R.string.enter_your_max_length_dialog),
                list = (10..60).map { it.toString() }
            )
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.enter_address_user),
                    style = MaterialTheme.typography.titleMedium,
                )
                Switch(
                    checked = personalState.value.addressUser,
                    onCheckedChange = { onChangeByKey(Personal.ADDRESS_USER_KEY, it) }
                )
            }
        }
        item {
            NextButton(
                modifier = Modifier.fillMaxWidth(0.9f),
                onClick = nextButtonClick,
                enabled = with(personalState.value) {
                    tone.isNotEmpty() && format.isNotEmpty() &&
                            maxLength != 0
                }
            )
        }
    }

}

@Composable
fun LastScreen(
    modifier: Modifier = Modifier,
    onSave: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DisplayText(stringResource(R.string.sign_up_last_screen))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Paddings.Small.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.mascot_motivation),
                contentDescription = "mascot",
                modifier = Modifier.size(350.dp)
            )
            Button(
                modifier = Modifier.fillMaxWidth(0.9f), onClick = onSave
            ) {
                Text(
                    text = stringResource(R.string.save),
                )
            }
        }
    }
}


@Composable
fun DisplayText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = text,
        style = MaterialTheme.typography.displayLarge,
        color = MaterialTheme.colorScheme.primary,
        textAlign = textAlign,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}


