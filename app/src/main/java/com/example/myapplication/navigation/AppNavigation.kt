package com.example.myapplication.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.view.screens.bottom.favorite.Favorites
import com.example.myapplication.view.screens.bottom.add.Add
import com.example.myapplication.view.screens.bottom.add.AddUIAction
import com.example.myapplication.view.screens.bottom.add.AddViewModel
import com.example.myapplication.view.screens.bottom.add.DrawerElement
import com.example.myapplication.view.screens.bottom.favorite.FavUIAction
import com.example.myapplication.view.screens.bottom.favorite.FavViewModel
import com.example.myapplication.view.screens.bottom.profile.Profile
import com.example.myapplication.view.screens.bottom.profile.ProfileNavigateBundle
import com.example.myapplication.view.screens.bottom.profile.ProfileViewModel
import com.google.gson.Gson


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(modifier: Modifier, navController: NavHostController) {
    var selectedIndex by remember { mutableIntStateOf(Routes.bottomBarRoutes.indexOf(Routes.Profile)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var lastRoute by remember { mutableStateOf(navBackStackEntry?.destination) }
    /*    val routeToIndex =
            Routes.bottomBarRoutes.mapIndexed { idx, item -> item to idx }.toMap()

        LaunchedEffect(navBackStackEntry) {
            navBackStackEntry?.destination?.route?.let { currentRoute ->
                routeToIndex[currentRoute]?.let { idx ->
                    selectedIndex = idx
                }
            }
        }*/

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                items = Routes.bottomBarRoutes,
                selectedIndex = selectedIndex,
                onItemSelected = {
                    selectedIndex = it
                    navigateTo(
                        navController,
                        Routes.bottomBarRoutes[it].takeIf { it != Routes.Add() }
                            ?: Routes.Add()
                    )
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            navController = navController,
            startDestination = Routes.Profile,
        ) {
            composable<Routes.Favorites>(
                enterTransition = {
                    slideIntoContainer(
                        if (lastRoute == Routes.Add())
                            AnimatedContentTransitionScope.SlideDirection.Left
                        else
                            AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(200, easing = LinearEasing)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        if (selectedIndex == Routes.bottomBarRoutes.indexOf(Routes.Profile))
                            AnimatedContentTransitionScope.SlideDirection.Left
                        else
                            AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(200, easing = LinearEasing)
                    )
                },
            ) {

                val viewModel = hiltViewModel<FavViewModel>()
                val onAction = viewModel::onAction
                val state by viewModel.uiState.collectAsState()
                onAction(
                    FavUIAction.SetNavigateFun { selectedDrawerElement, phrase ->
                        navigateTo(
                            navController = navController,
                            route = Routes.Add(
                                drawerElement = selectedDrawerElement,
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

            composable<Routes.Add>(
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(200, easing = LinearEasing)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(200, easing = LinearEasing)
                    )
                }
            ) {
                val args = it.toRoute<Routes.Add>()

                val viewModel = hiltViewModel<AddViewModel>()
                val onAction = viewModel::onAction
                val state by viewModel.uiState.collectAsState()
                onAction(
                    AddUIAction.ChangeSelectedDrawerElement(
                        when (args.drawerElement) {
                            DrawerElement.CreateOwn -> DrawerElement.CreateOwn
                            else -> DrawerElement.Generate
                        }
                    )
                )
                onAction(
                    AddUIAction.SelectTheme(
                        args.phrase?.theme ?: ""
                    )
                )
                Add(
                    modifier = modifier,
                    onAction = onAction,
                    uiState = state
                )
            }

            composable<Routes.Profile>(
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(200, easing = LinearEasing)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(200, easing = LinearEasing)
                    )
                }
            ) {
                val viewModel = hiltViewModel<ProfileViewModel>()
                val state by viewModel.uiState.collectAsState()
                val onAction = viewModel::onAction

                Profile(
                    modifier = modifier.background(color = MaterialTheme.colorScheme.background),
                    uiState = state,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<Routes>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = stringResource(item.labelId)) },
                label = { Text(stringResource(item.labelId)) },
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) }
            )
        }
    }
}

private fun navigateTo(
    navController: NavHostController,
    route: Routes
) {

    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}