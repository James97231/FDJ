package com.example.basictest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basictest.ui.theme.BasicTestTheme

enum class AnomalyType {
    EMPTY,
    ERROR,
    LOADING,
}

@Composable
fun AnomalyListComponent(
    anomalyType: AnomalyType,
    message: String,
    modifier: Modifier = Modifier,
    painter: Painter = rememberVectorPainter(image = Icons.Default.Settings),
    buttonText: String? = null,
    onButtonClicked: () -> Unit = {},
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Image or Icon
            when (anomalyType) {
                AnomalyType.EMPTY -> {
                    Icon(
                        painter = painter,
                        contentDescription = "Liste vide",
                        modifier =
                            Modifier
                                .size(120.dp)
                                .background(color = Color.LightGray, shape = CircleShape)
                                .padding(16.dp),
                    )
                }

                AnomalyType.ERROR -> {
                    Icon(
                        painter = painter,
                        contentDescription = "Erreur",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier =
                            Modifier
                                .size(120.dp)
                                .background(color = Color.Red, shape = CircleShape)
                                .padding(16.dp),
                    )
                }

                AnomalyType.LOADING -> {
                    // Loading Indicator
                    CircularProgressIndicator()
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Message Text
            Text(
                text = message,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
            )

            if (buttonText != null) {
                Spacer(modifier = Modifier.height(16.dp))

                // Action Button
                Button(onClick = onButtonClicked) {
                    Text(text = buttonText)
                }
            }
        }
    }
}

@Preview(name = "empty")
@Composable
private fun AnomalyListComponentEmptyPreview() {
    BasicTestTheme {
        AnomalyListComponent(
            anomalyType = AnomalyType.EMPTY,
            message = "Aucun élément disponible",
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            painter = rememberVectorPainter(Icons.Default.Search),
        )
    }
}

@Preview(name = "error")
@Composable
private fun AnomalyListComponentErrorPreview() {
    BasicTestTheme {
        AnomalyListComponent(
            anomalyType = AnomalyType.ERROR,
            message = "Pas de connexion internet",
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            painter = rememberVectorPainter(Icons.Default.Warning),
        )
    }
}

@Preview(name = "loading")
@Composable
private fun AnomalyListComponentLoadingPreview() {
    BasicTestTheme {
        AnomalyListComponent(
            anomalyType = AnomalyType.LOADING,
            message = "Chargement en cours...",
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            painter = rememberVectorPainter(Icons.Default.Search),
        )
    }
}
