package com.example.charactercreator.ui

import android.annotation.SuppressLint
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.charactercreator.R
import com.example.charactercreator.data.DataSource
import com.example.charactercreator.model.CharacterCreatorViewModel
import com.example.charactercreator.ui.theme.CharacterCreatorTheme

@Composable
fun CharacterCardScreen(
    onDoneButtonClicked: () -> Unit,
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
            text = "Character Card",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // This box serves as the card background with the class color
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(getCharacterColor(characterCreatorUiState.characterInfo.name))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Character Name & Cost Bar
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                getCharacterIcon(characterCreatorUiState.characterInfo.name)?.let { iconRes ->
                                    Icon(
                                        painter = painterResource(id = iconRes),
                                        contentDescription = null,
                                        tint = getCharacterColor(characterCreatorUiState.characterInfo.name),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.padding(4.dp))
                                }
                                Text(
                                    text = characterCreatorUiState.characterInfo.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Text(
                                text = "Cost: ${DataSource.MAX_STATS - characterCreatorViewModel.getPointsRemaining()}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Character Image
                    val imageRes = when (characterCreatorUiState.characterInfo.name) {
                        "Rocket" -> R.drawable.rocket
                        "Rogue" -> R.drawable.rogue
                        "Paladin" -> R.drawable.paladin
                        "Mage" -> R.drawable.mage
                        else -> R.drawable.rocket
                    }

                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Character Image",
                        modifier = Modifier
                            .size(180.dp)
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Character Stats
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Stats display
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "STA", fontWeight = FontWeight.Bold)
                                    Text(text = "${characterCreatorUiState.stats[0]}")
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "STR", fontWeight = FontWeight.Bold)
                                    Text(text = "${characterCreatorUiState.stats[1]}")
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "AGI", fontWeight = FontWeight.Bold)
                                    Text(text = "${characterCreatorUiState.stats[2]}")
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "INT", fontWeight = FontWeight.Bold)
                                    Text(text = "${characterCreatorUiState.stats[3]}")
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Special Ability
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Special Ability",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = getSpecialAbility(characterCreatorUiState.characterInfo.name),
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "${characterCreatorViewModel.getPhysicalDmg()}/${characterCreatorViewModel.getHitPoints()}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = "Power/Toughness",
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onBackButtonClicked) {
                Text("Back")
            }

            Button(onClick = onDoneButtonClicked) {
                Text("Done")
            }
        }
    }
}

@Composable
private fun getCharacterIcon(characterName: String): Int? {
    return when (characterName) {
        "Warrior" -> R.drawable.baseline_shield_24
        "Rogue" -> R.drawable.baseline_electric_bolt_24
        "Paladin" -> R.drawable.baseline_heart_broken_24
        "Mage" -> R.drawable.baseline_self_improvement_24
        else -> null
    }
}

private fun getCharacterColor(characterName: String): Color {
    return when (characterName) {
        "Warrior" -> Color(0xFFD32F2F) // Red
        "Rogue" -> Color(0xFF388E3C) // Green
        "Paladin" -> Color(0xFFFFB300) // Gold/Yellow
        "Mage" -> Color(0xFF1976D2) // Blue
        else -> Color.Gray
    }
}

private fun getSpecialAbility(characterName: String): String {
    return when (characterName) {
        "Warrior" -> "Berserker Rage: When health drops below 30%, gain +5 to physical damage for 10 seconds."
        "Rogue" -> "Shadow Step: Become invisible for 5 seconds and gain +8 to your next attack."
        "Paladin" -> "Divine Shield: Become immune to all damage for 3 seconds and heal 20% of max health."
        "Mage" -> "Arcane Burst: Deal area damage to all enemies within 8 yards and slow their movement by 30%."
        else -> "No special ability."
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun CharacterCardScreenPreview() {
    CharacterCreatorTheme {
        CharacterCardScreen(
            onDoneButtonClicked = {},
            onBackButtonClicked = {},
            characterCreatorViewModel = CharacterCreatorViewModel()
        )
    }
}