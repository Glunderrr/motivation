package com.example.myapplication.navigation

import Transition
import Transition.TransitionDirection
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.myapplication.view.screens.bottom.favorite.Favorites
import com.example.myapplication.view.screens.bottom.add.Add
import com.example.myapplication.view.screens.bottom.add.AddUIAction
import com.example.myapplication.view.screens.bottom.add.AddViewModel
import com.example.myapplication.view.screens.bottom.add.DrawerElement
import com.example.myapplication.view.screens.bottom.favorite.FavUIAction
import com.example.myapplication.view.screens.bottom.favorite.FavViewModel
import com.example.myapplication.view.screens.bottom.profile.Profile
import com.example.myapplication.view.screens.bottom.profile.ProfileViewModel


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(modifier: Modifier /*navController: NavHostController*/) {
    val backStack = rememberNavBackStack<Routes>(Routes.Profile)
    val bottomBarRoutes = listOf(
        Routes.Add(),
        Routes.Favorites,
        Routes.Profile
    )
    var selectedIndex by remember { mutableIntStateOf(bottomBarRoutes.indexOf(Routes.Profile)) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                items = bottomBarRoutes,
                selectedIndex = selectedIndex,
                onItemSelected = { item, index ->
                    selectedIndex = index
                    backStack.add(item)
                }
            )
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .fillMaxSize(),
            backStack = backStack,
            onBack = {
                backStack.removeLastOrNull()
                selectedIndex = bottomBarRoutes.indexOf(backStack.last())
            },
            transitionSpec = {
                val from = backStack.getOrNull(backStack.lastIndex - 1)
                val to = backStack.last()

                val fromIndex = from?.let { bottomBarRoutes.indexOf(it) } ?: -1
                val toIndex = bottomBarRoutes.indexOf(to)

                val direction = when {
                    toIndex > fromIndex -> TransitionDirection.LEFT_TO_RIGHT
                    toIndex < fromIndex -> TransitionDirection.RIGHT_TO_LEFT
                    else -> error("Invalid route transition from $from to $to")
                }

                Transition.create(direction).invoke(this@NavDisplay)

            },
            entryProvider = { key ->
                when (key) {
                    Routes.Add() -> NavEntry(key) {
                        val data = key as Routes.Add
                        val viewModel = hiltViewModel<AddViewModel>()
                        val onAction = viewModel::onAction
                        val state by viewModel.uiState.collectAsState()
                        onAction(
                            AddUIAction.ChangeSelectedDrawerElement(
                                if (data.drawerElement == DrawerElement.CreateOwn.route)
                                    DrawerElement.CreateOwn
                                else DrawerElement.Generate
                            )
                        )
                        onAction(
                            AddUIAction.SelectTheme(
                                data.phrase?.theme ?: ""
                            )
                        )
                        Add(
                            modifier = modifier,
                            onAction = onAction,
                            uiState = state
                        )

                    }

                    is Routes.Favorites -> NavEntry(key) {
                        val viewModel = hiltViewModel<FavViewModel>()
                        val onAction = viewModel::onAction
                        val state by viewModel.uiState.collectAsState()

                        onAction(
                            FavUIAction.SetNavigateFun { selectedDrawerElement, phrase ->
                                backStack.add(
                                    Routes.Add(
                                        drawerElement = selectedDrawerElement.route,
                                        phrase = phrase
                                    )
                                )
                            }
                        )

                        Favorites(
                            uiState = state,
                            onAction = onAction,
                            modifier = modifier,
                        )

                    }

                    is Routes.Profile -> NavEntry(key) {
                        val viewModel = hiltViewModel<ProfileViewModel>()
                        val state by viewModel.uiState.collectAsState()
                        /*  viewModel.onAction(
                            ProfileNavigateBundle(
                                args.phrase,
                                args.drawerElement
                            )
                        )*/
                        Profile(
                            modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                            uiState = state,
                            onAction = viewModel::onAction
                        )
                    }

                    else -> {
                        Log.wtf("AppNavigation", "Unknown route: $key")
                        NavEntry(key) {
                            Text("Unknown route: $key")
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun BottomNavigationBar(
    items: List<Routes>,
    selectedIndex: Int,
    onItemSelected: (Routes, Int) -> Unit
) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        item.getIcon(),
                        contentDescription = stringResource(item.labelId),
                    )
                },
                label = { Text(stringResource(item.labelId)) },
                selected = selectedIndex == index,
                onClick = { onItemSelected(item, index) }
            )
        }
    }
}