package com.odensala.hashtalk.timeline.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.odensala.hashtalk.R

@Composable
fun LogoutDialog(showDialog: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(stringResource(R.string.confirm_logout)) },
            text = { Text(stringResource(R.string.confirm_logout_desc)) },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text(stringResource(R.string.logout))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogoutConfirmationDialog() {
    LogoutDialog(
        showDialog = true,
        onConfirm = {},
        onDismiss = {}
    )
}