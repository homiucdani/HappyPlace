package com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_overview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happyplace.core.presentation.components.RoundImage
import com.example.happyplace.feature_happy_place.happy_place_presentation.happy_place_overview.HappyPlaceItemUi

@Composable
fun HappyPlaceCard(
    modifier: Modifier = Modifier,
    item: HappyPlaceItemUi,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onUpdateClick: () -> Unit
) {

    Row(
        modifier = modifier
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.White)
            .clickable {
                onItemClick()
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        RoundImage(
            modifier = Modifier
                .size(80.dp)
                .border(width = 2.dp, color = Color.Black.copy(alpha = 0.8f), shape = CircleShape)
                .padding(3.dp)
                .clip(CircleShape),
            photoBytes = item.photoBytes
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = item.description,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(
            modifier = Modifier
                .weight(weight = 0.2f)
                .padding(start = 15.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(20.dp)
                    .clickable { onDeleteClick() },
                tint = Color(0xFFC92F23)
            )

            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(20.dp)
                    .clickable {
                        onUpdateClick()
                    },
                tint = Color(0xFF2AAC30)
            )
        }
    }
}
