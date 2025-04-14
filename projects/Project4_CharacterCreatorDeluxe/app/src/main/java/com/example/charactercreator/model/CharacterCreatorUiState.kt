package com.example.charactercreator.model

import androidx.compose.runtime.Immutable
import com.example.charactercreator.data.DataSource

@Immutable
data class CharacterCreatorUiState(
    val characterInfo: CharacterInfo = CharacterInfo(),
    val stats: Array<Int> = Array(size = 4, init = {0}),
    val statsMap: Map<String, Int> = HashMap<String, Int>(),
    val maxPoints: Int = DataSource.MAX_STATS
)