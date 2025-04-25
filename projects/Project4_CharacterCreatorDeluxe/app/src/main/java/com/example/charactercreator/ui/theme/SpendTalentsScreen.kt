package com.example.charactercreator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.charactercreator.R
import com.example.charactercreator.data.DataSource
import com.example.charactercreator.data.StatDataSource
import com.example.charactercreator.model.CharacterCreatorViewModel
import com.example.charactercreator.model.StatNameIcon
import com.example.charactercreator.ui.theme.CharacterCreatorTheme

@Composable
fun SpendTalentsScreen(
    onNextButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    characterCreatorViewModel: CharacterCreatorViewModel,
    modifier: Modifier = Modifier
) {
    val characterCreatorUiState by characterCreatorViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Spend Talent Points: ${characterCreatorUiState.characterInfo.name}",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Points Remaining:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "${characterCreatorViewModel.getPointsRemaining()}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (characterCreatorViewModel.getPointsRemaining() <= 0)
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(count = 4, key = { it }) { index ->
                StatButtons(
                    statStuff = StatDataSource.statStuffs[index],
                    value = "${characterCreatorUiState.stats[index]}",
                    onPlusClick = { characterCreatorViewModel.incrementStat(index) },
                    onMinusClick = { characterCreatorViewModel.decrementStat(index) },
                    isPlusEnabled = characterCreatorViewModel.getPointsRemaining() > 0,
                    isMinusEnabled = characterCreatorUiState.stats[index] > 0
                )
            }
        }

        // Character Attributes Display
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            CharacterAttributes(characterCreatorViewModel, Modifier.padding(16.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onBackButtonClicked) {
                Text("Back")
            }

            Button(
                onClick = onNextButtonClicked,
                enabled = characterCreatorViewModel.getPointsRemaining() >= 0
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun StatButtons(
    statStuff: StatNameIcon,
    value: String,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    isPlusEnabled: Boolean = true,
    isMinusEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painterResource(statStuff.icon),
                    contentDescription = stringResource(statStuff.name),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(statStuff.name),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onMinusClick,
                    enabled = isMinusEnabled,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                        contentDescription = "Decrease"
                    )
                }

                Button(
                    onClick = onPlusClick,
                    enabled = isPlusEnabled,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_keyboard_arrow_up_24),
                        contentDescription = "Increase"
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterAttributes(
    characterCreatorViewModel: CharacterCreatorViewModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Character Attributes",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        DataSource.characterAttributes.forEachIndexed { index, attribute ->
            if (index > 0) { // Skip "Points remaining" as it's shown separately
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = attribute)
                    Text(
                        text = "${characterCreatorViewModel.getCharacterAttribute(attribute)}",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SpendTalentsScreenPreview() {
    CharacterCreatorTheme {
        SpendTalentsScreen(
            onNextButtonClicked = {},
            onBackButtonClicked = {},
            characterCreatorViewModel = CharacterCreatorViewModel()
        )
    }
}