package com.example.athometfgandroidcarloshernandez.ui.framework.screens.utils

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.athometfgandroidcarloshernandez.common.Constantes.ACEPTAR
import com.example.athometfgandroidcarloshernandez.common.Constantes.SALIR

@Composable
fun ConfirmationDialog(
    text: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = text) },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(ACEPTAR)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(SALIR)
            }
        }
    )
}