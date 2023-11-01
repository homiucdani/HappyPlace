package com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happyplace.R
import com.example.happyplace.core.presentation.util.UiText
import com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_overview.components.HappyPlaceCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HappyPlaceScreen(
    state: HappyPlaceState,
    snackbarHostState: SnackbarHostState,
    onEvent: (HappyPlaceEvent) -> Unit,
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = UiText.StringResources(R.string.app_name).asString(context),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                    .fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2A89D5))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(HappyPlaceEvent.AddHappyPlace)
                },
                containerColor = Color(0xFF2A89D5),
                elevation = FloatingActionButtonDefaults.elevation(5.dp),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(snackbarData = it)
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(
                items = state.happyPlaces,
                key = {item ->
                    item.id
                }
            ) { happyPlaceItem ->
                HappyPlaceCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItemPlacement(),
                    item = happyPlaceItem,
                    onItemClick = {
                        onEvent(
                            HappyPlaceEvent.ViewHappyPlace(
                                happyPlaceItem.id
                            )
                        )
                    },
                    onDeleteClick = {
                        onEvent(HappyPlaceEvent.DeleteHappyPlace(happyPlaceItem.id))

                        coroutineScope.launch {
                            val res = snackbarHostState.showSnackbar(
                                "Item deleted, undo action.",
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Short
                            )

                            if (res == SnackbarResult.ActionPerformed) {
                                onEvent(HappyPlaceEvent.RestoreHappyPlace)
                            }
                        }
                    },
                    onUpdateClick = {
                        onEvent(
                            HappyPlaceEvent.UpdateHappyPlace(
                                happyPlaceItem.id
                            )
                        )
                    }
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    strokeWidth = 4.dp,
                    color = Color(0xFF2A89D5)
                )
            }

            if (state.happyPlaces.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_happy_place_added),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )
            }
        }
    }
}