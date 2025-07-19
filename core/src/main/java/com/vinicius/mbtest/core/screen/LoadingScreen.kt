package com.vinicius.mbtest.core.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

const val LOADING_TEST_TAG = "LOADING"

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(LOADING_TEST_TAG),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}