package com.vinicius.mbtest.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreen(
    errorMessage: String,
    errorButtonText: String = "Tentar novamente",
    onClickRetry: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = onClickRetry) { 
            Text(text = errorButtonText)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ErrorScreenPreview() {
    ErrorScreen(errorMessage = "Erro de conexão") {}
}