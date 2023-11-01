package com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_details_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happyplace.core.presentation.components.RoundImage
import com.example.happyplace.feature_happy_place.happy_place_domain.util.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HappyPlaceDetailsView(
    state: HappyPlaceDetailsState,
    onEvent: (HappyPlaceDetailsEvent) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.title,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onEvent(HappyPlaceDetailsEvent.PopBackStack)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2A89D5)),
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                    .fillMaxWidth()

            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .size(260.dp)
                    .align(CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                RoundImage(
                    modifier = Modifier.fillMaxSize(),
                    photoBytes = state.photoBytes
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Description: ${state.description}",
                    color = Color.DarkGray.copy(alpha = 0.8f),
                    fontSize = 22.sp,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Location: ${state.locationName}",
                    color = Color(0xFF2B802F),
                    fontSize = 26.sp,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Date: ${formatDate(state.date)}",
                    fontSize = 22.sp,
                    modifier = Modifier.align(End)
                )
            }

            Button(
                onClick = {
                    onEvent(HappyPlaceDetailsEvent.NavigateToMap)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A89D5)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                Text(
                    text = "VIEW ON MAP",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}