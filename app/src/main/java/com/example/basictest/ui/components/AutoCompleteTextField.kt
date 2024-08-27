package com.example.basictest.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basictest.R
import com.example.basictest.ui.theme.BasicTestTheme

@Composable
fun AutoCompleteTextField(
    query: String,
    suggestions: List<String>,
    placeholder: String,
    actionItemSelected: (String) -> Unit,
    actionQueryChanged: (String) -> Unit,
    actionClearClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItem by remember { mutableStateOf<String?>(null) }

    val collapsed = { query.isBlank() || (selectedItem != null && query == selectedItem) }

    Log.e("AutoCompleteTextField", "recomposition :${query to suggestions}")

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
    ) {
        Column {
            Log.e("AutoCompleteTextField", "data: ${query to suggestions} ")
            TextField(
                value = TextFieldValue(query, TextRange(query.length)),
                onValueChange = {
                    Log.e("AutoCompleteTextField", "onValueChange  Query:$query->${it.text}")
                    actionQueryChanged(it.text)
                },
                placeholder = { Text(placeholder) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color.Gray,
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { actionClearClicked() }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear",
                                tint = Color.Gray,
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors =
                    TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(50)),
            )

            if (!collapsed()) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(suggestions.size) { index ->
                        val item = suggestions[index]
                        Card(
                            shape = RectangleShape,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedItem = item
                                        actionItemSelected(item)
                                    },
                        ) {
                            Text(
                                text = item,
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AutoCompleteTextFieldPreview() {
    val leagues =
        remember {
            listOf(
                "French Ligue 1",
                "English Premier League",
                "Spanish La Liga",
                "Italian Serie A",
                "German Bundesliga",
                "Portuguese Primeira Liga",
                "Dutch Eredivisie",
                "Belgian Pro League",
                "Greek Super League",
                "Turkish Super Lig",
                "Swiss Super League",
                "Scottish Premiership",
                "Russian Premier League",
                "Ukrainian Premier League",
                "Austrian Bundesliga",
                "Danish Superliga",
                "Swedish Allsvenskan",
                "Norwegian Eliteserien",
                "Finnish Veikkausliiga",
                "Irish Premier Division",
                "Welsh Premier League",
                "Northern Irish Premiership",
                "Polish Ekstraklasa",
                "Czech First League",
                "Slovak Super Liga",
                "Hungarian NB I",
                "Romanian Liga I",
                "Bulgarian First League",
                "Croatian Prva HNL",
                "Serbian SuperLiga",
                "Slovenian PrvaLiga",
                "Bosnian Premier Liga",
                "Montenegrin First League",
                "Albanian Superliga",
                "Macedonian First League",
                "Kosovar Superliga",
                "Luxembourg National Division",
                "Maltese Premier League",
                "Icelandic Premier League",
                "Faroe Islands Premier League",
                "Estonian Meistriliiga",
            )
        }

    BasicTestTheme {
        var query by remember { mutableStateOf("") }
        AutoCompleteTextField(
            query = query,
            suggestions = leagues,
            placeholder = stringResource(id = R.string.search_league_placeholder),
            actionItemSelected = { query = it },
            actionQueryChanged = { query = it },
            actionClearClicked = { query = "" },
        )
    }
}
