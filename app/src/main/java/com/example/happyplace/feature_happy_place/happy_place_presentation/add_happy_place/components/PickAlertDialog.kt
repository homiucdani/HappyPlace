package com.example.happyplace.feature_happy_place.happy_place_presentation.add_happy_place.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickAlertDialog(
    optionList: List<String>,
    onOptionClicked: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        modifier = Modifier
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column {
            optionList.forEachIndexed { index, option ->
                if (index < optionList.lastIndex) {
                    Text(
                        text = option,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable {
                                onOptionClicked(index)
                            }
                    )
                    Divider(modifier = Modifier.padding(horizontal = 5.dp))
                } else {
                    Text(
                        text = option,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable {
                                onOptionClicked(index)
                            }
                    )
                }
            }
        }
    }
}