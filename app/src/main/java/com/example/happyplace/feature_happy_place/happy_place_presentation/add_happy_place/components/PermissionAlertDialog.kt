package com.example.happyplace.feature_happy_place.happy_place_presentation.add_happy_place.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happyplace.feature_happy_place.happy_place_domain.permission.PermissionProvider

@Composable
fun PermissionAlertDialog(
    permissionProviderText: PermissionProvider,
    isPermanentlyDenied: Boolean,
    onDismissClick: () -> Unit,
    onOkClick: () -> Unit,
    onGrantPermission: () -> Unit
) {

    AlertDialog(
        onDismissRequest = {
            onDismissClick()
        },
        title = {
            Text(text = "Permission required")
        },
        text = {
            if (isPermanentlyDenied) {
                Text(text = permissionProviderText.getDescription(isPermanentlyDenied))
            } else {
                Text(text = permissionProviderText.getDescription(isPermanentlyDenied))
            }
        },
        confirmButton = {
            Column {
                Divider()
                Spacer(modifier = Modifier.height(10.dp))
                if (isPermanentlyDenied) {
                    Text(
                        text = "Grant permission",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onGrantPermission() })
                } else {
                    Text(
                        text = "Ok",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(10.dp)
                            .clickable { onOkClick() })
                }
            }
        }
    )
}