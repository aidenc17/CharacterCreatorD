package com.example.charactercreator.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.charactercreator.R
import com.example.charactercreator.data.DataSource
import com.example.charactercreator.model.CharacterCreatorViewModel
import com.example.charactercreator.model.CharacterInfo
import com.example.charactercreator.ui.theme.CharacterCreatorTheme

@Composable
fun SelectClassScreen(
    onNextButtonClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    characterCreatorViewModel: CharacterCreatorViewModel,
    modifier: Modifier = Modifier
) {
    val characterCreatorUiState by characterCreatorViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select Your Class",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            SelectCharacter(
                charInfo = characterCreatorUiState.characterInfo,
                options = DataSource.characterNames,
                onSelectionChanged = { charName -> characterCreatorViewModel.setCharacterName(charName) },
                onCancelClicked = { onCancelClicked() }
            )
        }

        // Display character image based on selection
        if (characterCreatorUiState.characterInfo.name.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val imageRes = when (characterCreatorUiState.characterInfo.name) {
                    "Warrior" -> R.drawable.rocket
                    "Rogue" -> R.drawable.rogue
                    "Paladin" -> R.drawable.paladin
                    "Mage" -> R.drawable.mage
                    else -> R.drawable.rocket
                }

                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Character Image",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(16.dp)
                )
            }
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onCancelClicked() }
            ) {
                Text("Cancel")
            }

            Button(
                onClick = { onNextButtonClicked() },
                enabled = characterCreatorUiState.characterInfo.name.isNotEmpty()
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun SelectCharacter(
    charInfo: CharacterInfo,
    options: Array<String>,
    onSelectionChanged: (String) -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primaryContainer),
        verticalArrangement = Arrangement.Top
    ) {
        options.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedValue == item,
                        onClick = {
                            selectedValue = item
                            onSelectionChanged(item)
                        }
                    )
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                )

                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    onCancelClicked()
                    selectedValue = ""
                }
            ) {
                Text("Clear")
            }

            Text(
                text = charInfo.name,
                modifier = Modifier.padding(top = 12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
fun SelectClassScreenPreview() {
    CharacterCreatorTheme {
        SelectClassScreen(
            onNextButtonClicked = {},
            onCancelClicked = {},
            characterCreatorViewModel = CharacterCreatorViewModel()
        )
    }
}
