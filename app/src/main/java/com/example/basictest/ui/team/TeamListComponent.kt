package com.example.basictest.ui.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.basictest.domain.model.Team
import com.example.basictest.ui.theme.BasicTestTheme

@Composable
fun TeamBadgeGrid(
    teams: List<Team>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        content = {
            items(teams.size) { index ->
                val team = teams[index]
                TeamBadgeItem(
                    item = team,
                    Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .background(Color.Transparent, RoundedCornerShape(16.dp))
                        .padding(8.dp),
                )
            }
        },
    )
}

@Composable
fun TeamBadgeItem(
    item: Team,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(model = item.strBadge),
            contentDescription = item.name,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier =
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(120.dp),
        )

        Text(
            text = item.name ?: "",
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
        )
    }
}

@Preview
@Composable
private fun TeamBadgeGridPreview() {
    val teams =
        remember {
            listOf(
                Team(
                    id = "133703",
                    name = "Toulouse",
                    strBadge = "https://www.thesportsdb.com/images/media/team/badge/17eqox1688449282.png",
                ),
                Team(
                    id = "133882",
                    name = "Strasbourg",
                    strBadge = "https://www.thesportsdb.com/images/media/team/badge/yuxtyy1464540071.png",
                ),
                Team(
                    id = "133934",
                    name = "Stade de Reims",
                    strBadge = "https://www.thesportsdb.com/images/media/team/badge/xcrw1b1592925946.png",
                ),
                Team(
                    id = "133717",
                    name = "St Etienne",
                    strBadge = "https://www.thesportsdb.com/images/media/team/badge/m4ej831656423694.png",
                ),
                Team(
                    id = "133719",
                    name = "Rennes",
                    strBadge = "https://www.thesportsdb.com/images/media/team/badge/ypturx1473504818.png",
                ),
                Team(
                    id = "133714",
                    name = "Paris SG",
                    strBadge = "https://www.thesportsdb.com/images/media/team/badge/rwqrrq1473504808.png",
                ),
                // Ajoutez plus d'équipes ici
            )
        }

    BasicTestTheme {
        Surface {
            TeamBadgeGrid(
                teams,
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            )
        }
    }
}
