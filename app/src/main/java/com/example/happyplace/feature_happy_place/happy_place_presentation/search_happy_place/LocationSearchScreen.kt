package com.example.happyplace.feature_happy_place.happy_place_presentation.search_happy_place


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.happyplace.feature_happy_place.happy_place_presentation.search_happy_place.components.SearchTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSearchScreen(
    queryState: String,
    state: LocationSearchState,
    onLocationResultClicked: (String) -> Unit,
    onEvent: (LocationSearchEvent) -> Unit
) {

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    SearchTextField(
                        value = queryState,
                        onValueChange = {
                            onEvent(LocationSearchEvent.OnLocationEntered(it))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onEvent(LocationSearchEvent.Close)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2A89D5)),
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                    .fillMaxWidth(),
                actions = {
                    Box(contentAlignment = Alignment.Center) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.CenterEnd)
                                    .padding(9.dp)
                            )
                        } else {
                            IconButton(
                                onClick = {
                                    onEvent(LocationSearchEvent.ClearLocationText)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            items(state.placesResult.searchList) { predictionText ->
                Text(
                    text = predictionText.getFullText(null).toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable {
                            onLocationResultClicked(
                                predictionText
                                    .getFullText(null)
                                    .toString()
                            )
                        }
                )
            }
        }
    }
}