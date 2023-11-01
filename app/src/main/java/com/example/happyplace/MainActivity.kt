package com.example.happyplace

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.happyplace.core.navigation.Routes
import com.example.happyplace.core.presentation.util.UiEvent
import com.example.happyplace.feature_happy_place.happy_place_presentation.add_happy_place.AddHappyPlaceEvent
import com.example.happyplace.feature_happy_place.happy_place_presentation.add_happy_place.AddHappyPlaceScreen
import com.example.happyplace.feature_happy_place.happy_place_presentation.add_happy_place.AddHappyPlaceViewModel
import com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_details_view.HappyPlaceDetailsEvent
import com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_details_view.HappyPlaceDetailsView
import com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_details_view.HappyPlaceDetailsViewModel
import com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_map.HappyPlaceMapScreen
import com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_map.HappyPlaceMapViewModel
import com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_overview.HappyPlaceEvent
import com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_overview.HappyPlaceScreen
import com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_overview.HappyPlaceViewModel
import com.example.happyplace.feature_happy_place.happy_place_presentation.search_happy_place.LocationSearchEvent
import com.example.happyplace.feature_happy_place.happy_place_presentation.search_happy_place.LocationSearchScreen
import com.example.happyplace.feature_happy_place.happy_place_presentation.search_happy_place.LocationSearchViewModel
import com.example.happyplace.ui.theme.HappyPlaceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyPlaceTheme {
                HappyPlaceNavigation()
            }
        }
    }
}

@Composable
fun HappyPlaceNavigation() {

    val navController = rememberNavController()
    val snackbarHostState = SnackbarHostState()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Routes.HAPPY_PLACE_SCREEN
    ) {

        composable(route = Routes.HAPPY_PLACE_SCREEN) {
            val viewModel: HappyPlaceViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()
            HappyPlaceScreen(
                state = state.value,
                onEvent = { event ->
                    when (event) {
                        HappyPlaceEvent.AddHappyPlace -> {
                            navController.navigate(Routes.ADD_HAPPY_PLACE_SCREEN + "/${-1}")
                        }

                        is HappyPlaceEvent.UpdateHappyPlace -> {
                            navController.navigate(Routes.ADD_HAPPY_PLACE_SCREEN + "/${event.id}")
                        }

                        is HappyPlaceEvent.ViewHappyPlace -> {
                            navController.navigate(Routes.VIEW_HAPPY_PLACE_PLACE_SCREEN + "/${event.id}")
                        }

                        else -> viewModel.onEvent(event)
                    }
                },
                snackbarHostState = snackbarHostState,
            )
        }

        composable(
            Routes.VIEW_HAPPY_PLACE_PLACE_SCREEN + "/{happyPlaceId}",
            arguments = listOf(
                navArgument("happyPlaceId") {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val viewModel: HappyPlaceDetailsViewModel = hiltViewModel()
            val state = viewModel.state.value
            HappyPlaceDetailsView(
                state = state,
                onEvent = { event ->
                    when (event) {
                        HappyPlaceDetailsEvent.PopBackStack -> {
                            navController.popBackStack()
                        }

                        HappyPlaceDetailsEvent.NavigateToMap -> {
                            navController.navigate(
                                Routes.MAP_SCREEN +
                                        "/${state.latitudeLongitude.latitude.toString()}/${state.latitudeLongitude.longitude.toString()}/${state.locationName}"
                            )
                        }
                    }
                }
            )
        }

        composable(
            route = Routes.MAP_SCREEN + "/{latitude}/{longitude}/{locationName}",
            arguments = listOf(
                navArgument("latitude") {
                    type = NavType.StringType
                },
                navArgument("longitude") {
                    type = NavType.StringType
                },
                navArgument("locationName") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val viewModel: HappyPlaceMapViewModel = hiltViewModel()
            val state = viewModel.state.value

            HappyPlaceMapScreen(
                state = state,
                popBackStack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Routes.ADD_HAPPY_PLACE_SCREEN + "/{happyPlaceId}",
            arguments = listOf(
                navArgument("happyPlaceId") {
                    type = NavType.IntType
                }
            )
        ) { backStack ->
            val viewModel: AddHappyPlaceViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState().value

            // retrieves id from main screen
            val happyPlaceId = backStack.arguments?.getInt("happyPlaceId")

            // retrieves location from search screen
            val locationResult =
                backStack.savedStateHandle.getStateFlow<String?>("locationResult", null)
                    .collectAsState()

            LaunchedEffect(key1 = locationResult) {
                viewModel.onEvent(AddHappyPlaceEvent.SubmittedLocationResult(locationResult.value))
                backStack.savedStateHandle["locationResult"] = null
            }

            LaunchedEffect(key1 = true) {
                viewModel.uiEvent.collectLatest { uiEvent ->
                    when (uiEvent) {
                        UiEvent.SaveHappyPlace -> {
                            navController.popBackStack()
                        }

                        is UiEvent.ShowSnackbar -> {
                            snackbarHostState.showSnackbar(
                                message = uiEvent.message,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            }

            AddHappyPlaceScreen(
                id = happyPlaceId ?: -1,
                popBackStack = {
                    navController.popBackStack()
                },
                state = state,
                onEvent = { event ->
                    when (event) {
                        AddHappyPlaceEvent.LocationSearchClick -> {
                            navController.navigate(Routes.LOCATION_SEARCH)
                        }

                        else -> viewModel.onEvent(event)
                    }
                },
                snackbarHostState = snackbarHostState,
                viewModel = viewModel
            )
        }

        composable(route = Routes.LOCATION_SEARCH) {
            val viewModel: LocationSearchViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState().value

            val queryState = viewModel.searchQuery.collectAsState().value

            LaunchedEffect(key1 = true) {
                viewModel.oneTimeEvent.collectLatest { event ->
                    when (event) {
                        is UiEvent.ShowSnackbar -> {
                            Toast.makeText(
                                context,
                                event.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        else -> Unit
                    }
                }
            }

            LocationSearchScreen(
                queryState = queryState,
                state = state,
                onLocationResultClicked = { locationResult ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "locationResult",
                        locationResult
                    )
                    navController.popBackStack()
                },
                onEvent = { event ->
                    when (event) {
                        LocationSearchEvent.Close -> {
                            navController.popBackStack()
                        }

                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
    }
}
