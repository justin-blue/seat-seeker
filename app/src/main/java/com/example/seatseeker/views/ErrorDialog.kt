package com.example.seatseeker.views


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.seatseeker.R


@Composable
fun ErrorDialog(
    onDialogErrorClosed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDialogErrorClosed() },
        confirmButton = {
            TextButton(onClick = { onDialogErrorClosed() })
            { Text(text = stringResource(R.string.retry)) }
        },
        title = { Text(text = stringResource((R.string.something_went_wrong))) },
        text = { Text(text = stringResource((R.string.error_message)) ) }
    )
}