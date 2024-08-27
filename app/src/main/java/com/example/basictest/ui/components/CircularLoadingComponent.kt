package com.example.basictest.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basictest.ui.theme.BasicTestTheme

@Composable
fun CircularLoadingComponent(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun CircularLoadingComponentPreview() {
    BasicTestTheme {
        CircularLoadingComponent(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        )
    }
}
